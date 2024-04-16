package program.database;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import program.model.ModelName;
import program.model.graph.*;
import program.model.metainfo.Consumer;
import program.model.metainfo.Line;
import program.model.metainfo.Source;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class PostgresHelper {
    private static final String URL = "jdbc:postgresql://localhost:5432/metaInfo";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private Connection connection;
    private static volatile PostgresHelper INSTANCE;

    private PostgresHelper(){
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public static PostgresHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (PostgresHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PostgresHelper();
                }
            }
        }
        return INSTANCE;
    }

    private void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ArrayList<Line> readLines() {
        ArrayList<Line> list = new ArrayList<>();

        try(Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Lines;")){

            while (rs.next()) {
                list.add(new Line(
                        rs.getInt("ID"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("throughput"),
                        rs.getInt("resistance"),
                        rs.getFloat("cost")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Source> readSources() {
        ArrayList<Source> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Sources;")) {

            while (rs.next()) {
                list.add(new Source(
                        rs.getInt("ID"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("max_gen"),
                        rs.getInt("min_gen"),
                        rs.getFloat("price"),
                        rs.getFloat("cost")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public ArrayList<Consumer> readConsumers() {
        ArrayList<Consumer> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Consumers;")) {

            while (rs.next()) {
                list.add(new Consumer(
                        rs.getInt("ID"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("load")
                ));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    //убрать в другой класс
    public boolean insertModel(Graph graph) {
        boolean isInserted = false;
        ObjectMapper objectMapper = new ObjectMapper();
        String name = "graph3";
        String elements = null;
        String style = null;
        String pan = null;
        String renderer = null;

        try (PreparedStatement st = connection.prepareStatement(
                "INSERT INTO model (name, elements, style, data, zoomingEnabled, userZoomingEnabled, zoom, minZoom," +
                        " maxZoom, panningEnabled, userPanningEnabled, pan, boxSelectionEnabled, renderer)" +
                        " VALUES (?, ?::jsonb, ?::jsonb, ?::jsonb, ?, ?, ?, ?, ?, ?, ?, ?::jsonb, ?, ?::jsonb);")) {
            elements = objectMapper.writeValueAsString(graph.getElement());
            style = objectMapper.writeValueAsString(graph.getStyleRules());
            pan = objectMapper.writeValueAsString(graph.getPosition());
            renderer = objectMapper.writeValueAsString(graph.getRenderer());

            st.setString(1, name);
            st.setString(2, elements);
            st.setString(3, style);
            st.setString(4, "{}");
            st.setBoolean(5, graph.isZoomingEnabled());
            st.setBoolean(6, graph.isUserZoomingEnabled());
            st.setDouble(7, graph.getZoom());
            st.setDouble(8, graph.getMinZoom());
            st.setDouble(9, graph.getMaxZoom());
            st.setBoolean(10, graph.isPanningEnabled());
            st.setBoolean(11, graph.isUserPanningEnabled());
            st.setString(12, pan);
            st.setBoolean(13, graph.isBoxSelectionEnabled());
            st.setString(14, renderer);

            isInserted = st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return isInserted;
    }

    public Graph readModelByName(String modelName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Graph graph = null;
        Element element = null;
        ArrayList<StyleRule> styleRules = null;
        Data data = null;
        Renderer renderer = null;
        Position pan = null;
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, StyleRule.class);

        try (PreparedStatement st = connection.prepareStatement("SELECT * FROM model m WHERE name = ?")) {
            st.setString(1, modelName);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    element = objectMapper.readValue(rs.getString("elements"), Element.class);
                    styleRules = objectMapper.readValue(rs.getString("style"), javaType);
                    data = objectMapper.readValue(rs.getString("data"), Data.class);
                    renderer = objectMapper.readValue(rs.getString("renderer"), Renderer.class);
                    pan = objectMapper.readValue(rs.getString("pan"), Position.class);
                    graph = new Graph(
                            rs.getString("name"),
                            element,
                            styleRules,
                            rs.getBoolean("boxSelectionEnabled"),
                            rs.getBoolean("userPanningEnabled"),
                            rs.getBoolean("panningEnabled"),
                            rs.getDouble("maxZoom"),
                            rs.getDouble("minZoom"),
                            rs.getDouble("zoom"),
                            rs.getBoolean("userZoomingEnabled"),
                            rs.getBoolean("zoomingEnabled"),
                            data,
                            renderer,
                            pan
                    );
                }
            } catch (JsonMappingException e) {
                throw new RuntimeException(e);
            } catch (JsonParseException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return graph;
    }

    public ModelName readModelNames () {
        ModelName modelName = new ModelName();
        try (Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT name FROM model m")) {
            while (rs.next()){
                modelName.addModelName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelName;
    }
}
