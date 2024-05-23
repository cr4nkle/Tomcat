package program;

import program.logic.xml.XmlGenerator;
import program.model.graph.EdgeData;
import program.model.graph.NodeData;
import program.model.locale.ModalConfig;
import program.utils.Constant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Main {
    public static void main(String[] args) throws IOException {
        // XmlGenerator xmlGenerator = new XmlGenerator();
        // xmlGenerator.generate();
//        ObjectMapper objectMapper = new ObjectMapper();
//        ModalConfig result;
//        try {
//            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(Constant.RU_LOCALE_PATH);
//            if (inputStream == null) {
//                throw new FileNotFoundException("File not found: " + Constant.RU_LOCALE_PATH);
//            }
//
//            result = objectMapper.readValue(inputStream, ModalConfig.class);
//            System.out.println(result);
//        } catch (IOException e) {
//
//            System.err.println("Failed to read JSON from " + Constant.RU_LOCALE_PATH + ": " + e.getMessage());
//
//        }
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        first.add("n1");
        first.add("n2");
        first.add("n3");
        first.add("n4");
        first.add("lim");

        second.add("e12");
        second.add("e23");
        second.add("e24");
        second.add("sign");
        second.add("lim");

        NodeData node1 = new NodeData("source", 2, 20, 5, 0, 0, "heat", true, 0.95f);
        NodeData node3 = new NodeData("consumer", 0, 0, 0, 0, 2, "heat", false, 0.95f);
        NodeData node4 = new NodeData("consumer", 0, 0, 0, 0, 3, "heat", false, 0.95f);
        NodeData node5 = new NodeData("connector", 0, 0, 0, 0, 0, "heat", false, 0.95f);

        EdgeData edge1 = new EdgeData("heat", 5, 0, 0, 10.0, "n1", "n2");
        EdgeData edge3 = new EdgeData("heat", 3, 0, 0, 14.0, "n2", "n3");
        EdgeData edge4 = new EdgeData("heat", 10, 0, 0, 17.0, "n2", "n4");
        EdgeData edge5 = new EdgeData("heat", 10, 0, 0, 17.0, "n1", "n2");
        EdgeData edge6 = new EdgeData("heat", 2, 0, 0, 17.0, "n1", "n2");

        HashMap<String, NodeData> node = new HashMap<>();
        HashMap<String, EdgeData> edge = new HashMap<>();
        node.put("n1", node1);
        node.put("n2", node5);
        node.put("n3", node3);
        node.put("n4", node4);

        edge.put("e12", edge1);
        edge.put("e12c", edge5);
        edge.put("e12cc", edge6);
        edge.put("e23", edge3);
        edge.put("e24", edge4);

//
//        LocalTime time1 = LocalTime.now();
////        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
////        ArrayList<Double> lim = new ArrayList<>();
////        ArrayList<Integer> sin = new ArrayList<>();
////        ArrayList<Double> min = new ArrayList<>();
////        ArrayList<Double> max = new ArrayList<>();
////        ArrayList<Double> goal = new ArrayList<>();
////        ArrayList<Boolean> type = new ArrayList<>();
////        goal.add(0.0);
////
////        first.forEach(f -> {
////            ArrayList<Double> row = new ArrayList<>();
////            row.add(0.0);
////            second.forEach(s -> {
////                if (node.containsKey(f)) {
////                    double v = 0;
////                    NodeData n = node.get(f);
////                    String nodeType = n.getNodeType();
////
////                    switch (s) {
////                        case "sin":
////                            v = (!nodeType.equals("source")) ? 3 : 1;
////                            sin.add((int) v);
////                            break;
////                        case "lim":
////                            v = (!nodeType.equals("source")) ? -n.getLoad() : 0;
////                            lim.add(v);
////                            break;
////                        default:
////                            EdgeData e = edge.get(s);
////                            boolean isSource = f.equals(e.getSource());
////                            boolean isTarget = f.equals(e.getTarget());
////                            if (isSource) {
////                                v = s.endsWith("d") ? -n.getMaxGen() : 1;
////                            } else if (isTarget) {
////                                v = s.endsWith("d") ? 0 : -1;
////                            }
////                            row.add(v);
////                            break;
////                    }
////                } else {
////                    switch (s) {
////                        case "sin":
////                            lim.add(1.0);
////                            break;
////                        case "lim":
////                            sin.add(1);
////                            break;
////                        default:
////                            row.add(s.endsWith("d") ? 1.0 : 0.0);
////                            break;
////                    }
////                }
////            });
////            matrix.add(row);
////        });
////
////        second.remove(second.size() - 1);
////        second.remove(second.size() - 1);
////        first.remove(first.size() - 1);
////
////        second.forEach(s -> {
////            first.forEach(t -> {
////                NodeData n = node.get(t);
////                EdgeData e = edge.get(s);
////                boolean isSource = t.equals(e.getSource());
////                boolean isTarget = t.equals(e.getTarget());
////                String nodeType = n.getNodeType();
////
////                if (isSource) {
////                    if (s.endsWith("d")) {
////                        min.add(0.0);
////                        max.add(1.0);
////                        goal.add((double) n.getCost());
////                        type.add(true);
////                    } else if (nodeType.equals("source")) {
////                        min.add((double) n.getMinGen());
////                        max.add((double) n.getMaxGen());
////                        goal.add((double) n.getPrice());
////                        type.add(false);
////                    }
////                } else if (isTarget && nodeType.equals("consumer")) {
////                    min.add((double) n.getMinGen());
////                    max.add((double) n.getLoad());
////                    goal.add((double) n.getCost());
////                    type.add(false);
////                }
////            });
////        });
//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        Problem problem = new Problem(first, second, node, edge);
//        MathStatement mathStatement = LinearStatement.getLinearStatement(problem);
//        ArrayList<Double> l = new ArrayList<>();
//        l.add(6.0);
//        l.add(0.0);
//        l.add(-2.0);
//        l.add(-3.0);
//        l.add(0.0);
//        ArrayList<Double> g = new ArrayList<>();
//        g.add(0.0);
//        g.add(0.0);
//        g.add(2.0);
//        g.add(3.0);
//        ArrayList<Double> m = new ArrayList<>();
//        m.add(10.0);
//        m.add(10.0);
//        m.add(10.0);
//
////        mathStatement.setLim(l);
////        mathStatement.setGoal(g);
////        mathStatement.setMax(m);
//        Solution solution = optimate(mathStatement);// вызываем функцию оптимизации
////        System.out.println(getObjective());// выводим значение целевой функции
////        System.out.println(Arrays.toString(getVariables()));// выводим значения переменных
//        LocalTime time2 = LocalTime.now();
//        Duration duration = Duration.between(time1, time2);
//        System.out.println(duration.toMillis() + " миллисекунд");
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String str = objectMapper.writeValueAsString(solution);
//        System.out.println(str);

    }
}