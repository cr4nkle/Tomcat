package program.database;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import program.model.ModelName;
import program.model.graph.*;
import program.model.style.StyleRule;
import program.utils.Constant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class PostgresFirstHandler {
    private Connection connection;
    private static volatile PostgresFirstHandler INSTANCE;
//не используется
    private PostgresFirstHandler(){
        try {
            this.connection = DriverManager.getConnection(
                    Constant.FIRST_CLIENT_URL, Constant.FIRST_CLIENT_USER, Constant.FIRST_CLIENT_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public static PostgresFirstHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (PostgresFirstHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PostgresFirstHandler();
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
//                            rs.getString("name"),
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
