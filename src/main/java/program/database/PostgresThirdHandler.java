package program.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import program.model.ModelName;
import program.model.compressedGraph.*;
import program.model.graph.Position;
import program.model.graph.StyleRule;
import program.utils.Constant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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

    public int insertModel(Graph graph) {
        AtomicInteger updatedRows = new AtomicInteger();
        String name = graph.getName();
        ArrayList<Node> nodes = graph.getElements().getNodes();
        ArrayList<Edge> edges = graph.getElements().getEdges();

        try {
            PreparedStatement prSt = connection.prepareStatement("INSERT INTO model (name) VALUES(?);");
            prSt.setString(1, name);
            updatedRows.addAndGet(prSt.executeUpdate());

            nodes.forEach(node -> {
                updatedRows.addAndGet(insertNode(name, node));
            });

            edges.forEach(edge -> {
                updatedRows.addAndGet(insertEdge(name, edge));
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return updatedRows.get();
    }


    //список вызывать в цикле
    private int insertNode(String name, Node node) {
        int updatedRows;
        ObjectMapper objectMapper = new ObjectMapper();
        String position;
        String equipment;

        try {
            PreparedStatement prSt = connection.prepareStatement("INSERT INTO nodes (id, model_name," +
                    " nodetype, systemtype, position, selected, equipment) VALUES(?, ?, ?, ?, ?::jsonb, ?, ?::jsonb);");
            position = objectMapper.writeValueAsString(node.getPosition());
            equipment = objectMapper.writeValueAsString(node.getData().getEquipment());

            prSt.setString(1, node.getData().getId());
            prSt.setString(2, name);
            prSt.setString(3, node.getData().getNodeType());
            prSt.setString(4, node.getData().getSystemType());
            prSt.setString(5, position);
            prSt.setBoolean(6, node.isSelected());
            prSt.setString(7, equipment);
            updatedRows = prSt.executeUpdate();
        } catch (SQLException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return updatedRows;
    }

    private int insertEdge(String name, Edge edge) {
        int updatedRows;
        ObjectMapper objectMapper = new ObjectMapper();
        String equipment;

        try {
            PreparedStatement prSt = connection.prepareStatement("INSERT INTO edges (id, model_name, source, target," +
                    " systemtype, length, selected, equipment) VALUES(?, ?, ?, ?, ?, ?, ?, ?::jsonb);");
            equipment = objectMapper.writeValueAsString(edge.getData().getEquipment());

            prSt.setString(1, edge.getData().getId());
            prSt.setString(2, name);
            prSt.setString(3, edge.getData().getSource());
            prSt.setString(4, edge.getData().getTarget());
            prSt.setString(5, edge.getData().getSystemType());
            prSt.setInt(6, edge.getData().getLength());
            prSt.setBoolean(7, edge.isSelected());
            prSt.setString(8, equipment);
            updatedRows = prSt.executeUpdate();
        } catch (SQLException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return updatedRows;
    }

    public Graph readModelByName(String name) {
        ArrayList<Node> nodes = readNodes(name);
        ArrayList<Edge> edges = readEdges(name);
        Element element = new Element(nodes, edges);
        Graph graph = new Graph(name, element);

        return graph;
    }

    private ArrayList<Node> readNodes(String modelName) {
        ArrayList<Node> nodes = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Equipment.class);

        try {
            PreparedStatement st = connection.prepareStatement("select * from nodes where model_name = ?");
            st.setString(1, modelName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Position position = objectMapper.readValue(rs.getString("position"), Position.class);
                ArrayList<Equipment> equipment = objectMapper.readValue(rs.getString("equipment"), javaType);
                Data data = new Data(
                        rs.getString("id"),
                        rs.getString("nodetype"),
                        null,
                        null,
                        rs.getString("systemtype"),
                        0,
                        equipment
                );
                nodes.add(new Node(
                        data,
                        position,
                        rs.getBoolean("selected")
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
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Equipment.class);

        try {
            PreparedStatement st = connection.prepareStatement("select * from edges where model_name = ?");
            st.setString(1, modelName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Position position = new Position(0, 0);
                ArrayList<Equipment> equipment = objectMapper.readValue(rs.getString("equipment"), javaType);
                Data data = new Data(
                        rs.getString("id"),
                        null,
                        rs.getString("source"),
                        rs.getString("target"),
                        rs.getString("systemtype"),
                        rs.getInt("length"),
                        equipment
                );
                edges.add(new Edge(
                        data,
                        position,
                        rs.getBoolean("selected")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return edges;
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

//    public void updateModel(Graph graph) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            PreparedStatement st = connection.prepareStatement("UPDATE model SET data=?, zoomingenabled=?," +
//                    " userzoomingenabled=?, zoom=?, minzoom=?, maxzoom=?, panningenabled=?, userpanningenabled=?, pan=?," +
//                    " boxselectionenabled=?, renderer=? WHERE name=?;");
//            String data = objectMapper.writeValueAsString(graph.getData());
//            String pan = objectMapper.writeValueAsString(graph.getPosition());
//            String renderer = objectMapper.writeValueAsString(graph.getRenderer());
//
//            st.setString(1, data);
//            st.setBoolean(2, graph.isZoomingEnabled());
//            st.setBoolean(3, graph.isUserZoomingEnabled());
//            st.setDouble(4, graph.getZoom());
//            st.setDouble(5, graph.getMinZoom());
//            st.setDouble(6, graph.getMaxZoom());
//            st.setBoolean(7, graph.isPanningEnabled());
//            st.setBoolean(8, graph.isUserPanningEnabled());
//            st.setString(9, pan);
//            st.setBoolean(10, graph.isBoxSelectionEnabled());
//            st.setString(11, renderer);
////            st.setString(12, graph.getName());
//            st.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void updateNode(Node node) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            PreparedStatement st = connection.prepareStatement("UPDATE nodes SET viz_id=?, model_name=?," +
//                    " nodetype=?, systemtype=?, position=?, group=?, removed=?, selected=?, selectable=?," +
//                    " locked=?, grabbable=?, pannable=?, classes=? WHERE id=?;");
//            String position = objectMapper.writeValueAsString(node.getPosition());
//
//            //нужно перепроверить
//            st.setString(1, node.getData().getId());
//            st.setString(2, "");
//            st.setString(2, node.getData().getNodeType());
//            st.setString(3, node.getData().getSystemType());
//            st.setString(4, position);
//            st.setString(5, node.getGroup());
//            st.setBoolean(6, node.isRemoved());
//            st.setBoolean(8, node.isSelected());
//            st.setBoolean(9, node.isSelectable());
//            st.setBoolean(10, node.isLocked());
//            st.setBoolean(11, node.isGrabbable());
//            st.setBoolean(12, node.isPannable());
//            st.setString(13, node.getClasses());
//            st.setInt(14, node.getIdFromDB());
//            st.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void updateEdge(Edge edge) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            PreparedStatement st = connection.prepareStatement("UPDATE edges SET viz_id=?, model_name=?, source=?," +
//                    " target=?, systemtype=?, length=?, position=?, group=?, removed=?, selected=?," +
//                    " selectable=?, locked=?, grabbable=?, pannable=?, classes=? WHERE id=?;");
//            String position = objectMapper.writeValueAsString(edge.getPosition());
//
//            //нужно перепроверить
//            st.setString(1, edge.getData().getId());
//            st.setString(2, edge.getData().getSource());
//            st.setString(3, edge.getData().getTarget());
//            st.setString(4, edge.getData().getSystemType());
//            st.setInt(5, edge.getData().getLength());
//            st.setString(6, position);
//            st.setString(7, edge.getGroup());
//            st.setBoolean(8, edge.isRemoved());
//            st.setBoolean(9, edge.isSelected());
//            st.setBoolean(10, edge.isSelectable());
//            st.setBoolean(11, edge.isLocked());
//            st.setBoolean(12, edge.isGrabbable());
//            st.setBoolean(13, edge.isPannable());
//            st.setString(14, edge.getClasses());
//            st.setInt(15, edge.getIdFromDB());
//            st.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void updateEquipment(Equipment equipment) {
//        //нужно отдавать id узла и ребра либо также через null
//        try {
//            PreparedStatement st = connection.prepareStatement("UPDATE equipment SET name=?, max_gen=?, min_gen=?," +
//                    " price=?, cost=?, throughput=?, resistance=?, load=?, node_id=?, edge_id=? WHERE id=?;");
//            st.setString(1, equipment.getName());
//            st.setInt(2, equipment.getMaxGen());
//            st.setInt(3, equipment.getMinGen());
//            st.setFloat(4, equipment.getPrice());
//            st.setFloat(5, equipment.getCost());
//            st.setInt(6, equipment.getThroughput());
//            st.setInt(7, equipment.getResistance());
//            st.setInt(8, equipment.getLoad());
//            st.setInt(9, 1);
//            st.setInt(10, 1);
//            st.setInt(11, equipment.getIdFromDB());
//            st.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public int deleteModel(String name) {
        int updatedRows;
        try {
            PreparedStatement prst = connection.prepareStatement("DELETE FROM model WHERE name = ?;");
            prst.setString(1, name);
            updatedRows = prst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  updatedRows;
    }

//    public void deleteNode(int id) {
//        try {
//            PreparedStatement st = connection.prepareStatement("");
//            st.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void deleteEdge(Edge edge) {
//        try {
//            PreparedStatement st = connection.prepareStatement("");
//            st.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void deleteEquipment(int id) {
//        try {
//            PreparedStatement st = connection.prepareStatement("");
//            st.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
