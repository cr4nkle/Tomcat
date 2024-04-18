package program.database;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import program.model.ModelName;
import program.model.graph.*;
import program.utils.Constant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class PostgresThirdHandler {
    private Connection connection;
    private static volatile PostgresThirdHandler INSTANCE;

    private PostgresThirdHandler() {
        try {
            this.connection = DriverManager.getConnection(
                    Constant.THIRD_CLIENT_URL, Constant.THIRD_CLIENT_USER, Constant.THIRD_CLIENT_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to establish database connection", e);
        }
    }

    public static PostgresThirdHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (PostgresThirdHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PostgresThirdHandler();
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
        ObjectMapper objectMapper = new ObjectMapper();
//        String name = "graph3";//заменить
        String pan;
        String renderer;

        try {
            PreparedStatement st = connection.prepareStatement(
                    "INSERT INTO model (name, data, zoomingEnabled, userZoomingEnabled, zoom, minZoom, maxZoom," +
                            " panningEnabled, userPanningEnabled, pan, boxSelectionEnabled, renderer)\n" +
                            "VALUES (?, ?::jsonb, ?, ?, ?, ?, ?, ?, ?, ?::jsonb, ?, ?::jsonb);");
            pan = objectMapper.writeValueAsString(graph.getPosition());
            renderer = objectMapper.writeValueAsString(graph.getRenderer());

            st.setString(1, graph.getName());
            st.setString(2, "{}");
            st.setBoolean(3, graph.isZoomingEnabled());
            st.setBoolean(4, graph.isUserZoomingEnabled());
            st.setDouble(5, graph.getZoom());
            st.setDouble(6, graph.getMinZoom());
            st.setDouble(7, graph.getMaxZoom());
            st.setBoolean(8, graph.isPanningEnabled());
            st.setBoolean(9, graph.isUserPanningEnabled());
            st.setString(10, pan);
            st.setBoolean(11, graph.isBoxSelectionEnabled());
            st.setString(12, renderer);
            st.executeUpdate();

            ArrayList<StyleRule> styleRules = graph.getStyleRules();
            styleRules.forEach(styleRule -> {
                insertStyleRule(graph.getName(), styleRule);
            });

            ArrayList<Node> nodes = graph.getElement().getNodes();
            nodes.forEach(node -> {
                insertNode(graph.getName(), node);
            });

            ArrayList<Edge> edges = graph.getElement().getEdges();
            edges.forEach(edge -> {
                insertEdge(graph.getName(), edge);
            });

        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }

        return true;
    }

    //список вызывать в цикле
    private void insertNode(String modelName, Node node) {
        ObjectMapper objectMapper = new ObjectMapper();
        int generatedKey = 0;

        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO nodes (model_name, viz_id, nodetype," +
                    " systemtype, position, \"group\", removed, selected, selectable, locked," +
                    " grabbable, pannable, classes) VALUES (?, ?, ?, ?, ?::jsonb, ?, ?, ?, ?, ?, ?, ?, ?)" +
                    " RETURNING id;", Statement.RETURN_GENERATED_KEYS);

            String position = objectMapper.writeValueAsString(node.getPosition());

            st.setString(1, modelName);
            st.setString(2, node.getData().getId());
            st.setString(3, node.getData().getNodeType());
            st.setString(4, node.getData().getSystemType());
            st.setString(5, position);
            st.setString(6, node.getGroup());
            st.setBoolean(7, node.isRemoved());
            st.setBoolean(8, node.isSelected());
            st.setBoolean(9, node.isSelectable());
            st.setBoolean(10, node.isLocked());
            st.setBoolean(11, node.isGrabbable());
            st.setBoolean(12, node.isPannable());
            st.setString(13, node.getClasses());
            st.executeUpdate();

            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            }

            ArrayList<Equipment> equipments = node.getData().getEquipment();
            int finalGeneratedKey = generatedKey;
            equipments.forEach(equipment -> {
                insertEquipment(true, equipment, finalGeneratedKey);
            });

        } catch (SQLException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertEdge(String modelName, Edge edge) {
        ObjectMapper objectMapper = new ObjectMapper();
        int generatedKey = 0;

        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO edges (viz_id, model_name, source, target," +
                    " systemtype, length, position, \"group\", removed, selected, selectable, locked, grabbable," +
                    " pannable, classes) VALUES (?, ?, ?, ?, ?, ?, ?::jsonb, ?, ?, ?, ?, ?, ?, ?, ?)" +
                    " RETURNING id;", Statement.RETURN_GENERATED_KEYS);

            String position = objectMapper.writeValueAsString(edge.getPosition());

            st.setString(1, edge.getData().getId());
            st.setString(2, modelName);
            st.setString(3, edge.getData().getSource());
            st.setString(4, edge.getData().getTarget());
            st.setString(5, edge.getData().getSystemType());
            st.setInt(6, edge.getData().getLength());
            st.setString(7, position);
            st.setString(8, edge.getGroup());
            st.setBoolean(9, edge.isRemoved());
            st.setBoolean(10, edge.isSelected());
            st.setBoolean(11, edge.isSelectable());
            st.setBoolean(12, edge.isLocked());
            st.setBoolean(13, edge.isGrabbable());
            st.setBoolean(14, edge.isPannable());
            st.setString(15, edge.getClasses());
            st.executeUpdate();

            ResultSet generatedKeys = st.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            }

            ArrayList<Equipment> equipments = edge.getData().getEquipment();
            int finalGeneratedKey = generatedKey;
            System.out.println(finalGeneratedKey);
            equipments.forEach(equipment -> {
                insertEquipment(false, equipment, finalGeneratedKey);
            });

        } catch (SQLException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertEquipment(Boolean isNode, Equipment equipment, int generatedKey) {

        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO equipment (name, max_gen, min_gen," +
                    " price, cost, throughput, resistance, load, node_id, edge_id)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

            st.setString(1, equipment.getName());
            st.setInt(2, equipment.getMaxGen());
            st.setInt(3, equipment.getMinGen());
            st.setFloat(4, equipment.getPrice());
            st.setInt(5, equipment.getCost());
            st.setInt(6, equipment.getThroughput());
            st.setInt(7, equipment.getResistance());
            st.setInt(8, equipment.getLoad());
            if (isNode){
                st.setInt(9, generatedKey);
                st.setNull(10, Types.INTEGER);
            }else{
                st.setNull(9, Types.INTEGER);
                st.setInt(10, generatedKey);
            }

            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertStyleRule(String modelName, StyleRule styleRule) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO style (model_name," +
                    " selector, style) VALUES (?, ?, ?::jsonb);");

            String style = objectMapper.writeValueAsString(styleRule.getStyle());
            st.setString(1, modelName);
            st.setString(2, styleRule.getSelector());
            st.setString(3, style);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Graph readModelByName(String modelName) {
        ObjectMapper objectMapper = new ObjectMapper();
        Graph graph = null;
        ArrayList<Node> nodes = readNodes(modelName);
        ArrayList<Edge> edges = readEdges(modelName);
        ArrayList<StyleRule> styleRules = readStyle(modelName);
        Element element = new Element(nodes, edges);
        Data data;
        Renderer renderer;
        Position pan;

        try {
            PreparedStatement st = connection.prepareStatement("select * from model where name = ?");
            st.setString(1, modelName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return graph;
    }

    private ArrayList<Node> readNodes(String modelName) {
        ArrayList<Node> nodes = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PreparedStatement st = connection.prepareStatement("select * from nodes where model_name = ?");
            st.setString(1, modelName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Position position = objectMapper.readValue(rs.getString("position"), Position.class);
                ArrayList<Equipment> equipments = readNodeEquipment(id);
                Data data = new Data(
                        rs.getString("viz_id"),
                        rs.getString("nodetype"),
                        null,
                        null,
                        rs.getString("systemtype"),
                        0,
                        equipments
                );
                nodes.add(new Node(
                        rs.getInt("id"),
                        data,
                        position,
                        rs.getString("group"),
                        rs.getBoolean("removed"),
                        rs.getBoolean("selected"),
                        rs.getBoolean("selectable"),
                        rs.getBoolean("locked"),
                        rs.getBoolean("grabbable"),
                        rs.getBoolean("pannable"),
                        rs.getString("classes")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nodes;
    }

    private ArrayList<Edge> readEdges(String modelName) {
        ArrayList<Edge> edges = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PreparedStatement st = connection.prepareStatement("select * from edges where model_name = ?");
            st.setString(1, modelName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Position position = objectMapper.readValue(rs.getString("position"), Position.class);
                ArrayList<Equipment> equipments = readEdgeEquipment(id);
                Data data = new Data(
                        rs.getString("viz_id"),
                        null,
                        rs.getString("source"),
                        rs.getString("target"),
                        rs.getString("systemtype"),
                        rs.getInt("length"),
                        equipments
                );
                edges.add(new Edge(
                        rs.getInt("id"),
                        data,
                        position,
                        rs.getString("group"),
                        rs.getBoolean("removed"),
                        rs.getBoolean("selected"),
                        rs.getBoolean("selectable"),
                        rs.getBoolean("locked"),
                        rs.getBoolean("grabbable"),
                        rs.getBoolean("pannable"),
                        rs.getString("classes")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return edges;
    }

    private ArrayList<Equipment> readEdgeEquipment(int id) {
        ArrayList<Equipment> equipments = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("select * from equipment e where edge_id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                equipments.add(new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("throughput"),
                        rs.getInt("resistance"),
                        rs.getInt("cost"),
                        rs.getInt("max_gen"),
                        rs.getInt("min_gen"),
                        rs.getInt("load")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return equipments;
    }

    private ArrayList<Equipment> readNodeEquipment(int id) {
        ArrayList<Equipment> equipments = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement("select * from equipment e where node_id = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                equipments.add(new Equipment(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("throughput"),
                        rs.getInt("resistance"),
                        rs.getInt("cost"),
                        rs.getInt("max_gen"),
                        rs.getInt("min_gen"),
                        rs.getInt("load")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return equipments;
    }

    private ArrayList<StyleRule> readStyle(String modelName) {
        ArrayList<StyleRule> styleRules = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            PreparedStatement st = connection.prepareStatement("select * from style where model_name = ?");
            st.setString(1, modelName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Style style = objectMapper.readValue(rs.getString("style"), Style.class);
                styleRules.add(new StyleRule(
                        rs.getInt("id"),
                        rs.getString("selector"),
                        style
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return styleRules;
    }

    public ModelName readModelNames() {
        ModelName modelName = new ModelName();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT name FROM model m")) {
            while (rs.next()) {
                modelName.addModelName(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return modelName;
    }
}
