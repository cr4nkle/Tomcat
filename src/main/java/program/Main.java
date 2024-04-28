package program;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import program.logic.solurion.LinearStatement;
import program.model.compressedGraph.EdgeData;
import program.model.compressedGraph.NodeData;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;
import program.model.mathStatement.Solution;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;

import static program.logic.solver.LinearSolver.*;


public class Main {
    public static void main(String[] args) throws IOException {


        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        first.add("n1");
        first.add("n1c");
        first.add("n1cc");
        first.add("n2");
        first.add("n3");
        first.add("n4");
        first.add("n5");
        first.add("n6");
        first.add("n7");
        first.add("n8");
        first.add("n9");
        first.add("n10");
        first.add("lim");

        second.add("e12");
        second.add("e12c");
        second.add("e12cc");
        second.add("e23");
        second.add("e24");
        second.add("e25");
        second.add("e26");
        second.add("e27");
        second.add("e28");
        second.add("e89");
        second.add("e810");
        second.add("e12d");
        second.add("e12cd");
        second.add("e12ccd");
        second.add("sign");
        second.add("lim");

        NodeData node1 = new NodeData("source", 2, 20, 5, 0, 0, "heat", true);
        NodeData node2 = new NodeData("source", 3, 30, 6, 0, 0, "heat", true);
        NodeData node3 = new NodeData("consumer", 0, 0, 0, 0, 2, "heat", false);
        NodeData node4 = new NodeData("consumer", 0, 0, 0, 0, 3, "heat", false);
        NodeData node5 = new NodeData("connector", 0, 0, 0, 0, 0, "heat", false);
        NodeData node6 = new NodeData("source", 5, 40, 10, 0, 0, "heat", true);

        EdgeData edge1 = new EdgeData("heat", 5, 0, 0, 10.0, "n1", "n2");
        EdgeData edge2 = new EdgeData("heat", 5, 0, 0, 10.0, "n1c", "n2");
        EdgeData edge3 = new EdgeData("heat", 3, 0, 0, 14.0, "n2", "n3");
        EdgeData edge4 = new EdgeData("heat", 4, 0, 0, 17.0, "n2", "n4");
        EdgeData edge5 = new EdgeData("heat", 4, 0, 0, 17.0, "n2", "n5");
        EdgeData edge6 = new EdgeData("heat", 4, 0, 0, 17.0, "n2", "n6");
        EdgeData edge7 = new EdgeData("heat", 4, 0, 0, 17.0, "n2", "n7");
        EdgeData edge8 = new EdgeData("heat", 4, 0, 0, 17.0, "n2", "n8");
        EdgeData edge9 = new EdgeData("heat", 4, 0, 0, 17.0, "n8", "n9");
        EdgeData edge10 = new EdgeData("heat", 4, 0, 0, 17.0, "n8", "n10");
        EdgeData edge11 = new EdgeData("heat", 5, 0, 0, 10.0, "n1cc", "n2");
        HashMap<String, NodeData> node = new HashMap<>();
        HashMap<String, EdgeData> edge = new HashMap<>();
        node.put("n1", node1);
        node.put("n1c", node2);
        node.put("n1cc", node6);
        node.put("n2", node5);
        node.put("n3", node3);
        node.put("n4", node4);
        node.put("n5", node4);
        node.put("n6", node4);
        node.put("n7", node4);
        node.put("n8", node5);
        node.put("n9", node3);
        node.put("n10", node3);

        edge.put("e12", edge1);
        edge.put("e12c", edge2);
        edge.put("e12cc", edge11);
        edge.put("e23", edge3);
        edge.put("e24", edge4);
        edge.put("e25", edge5);
        edge.put("e26", edge6);
        edge.put("e27", edge7);
        edge.put("e28", edge8);
        edge.put("e89", edge9);
        edge.put("e810", edge10);
        edge.put("e12d", edge1);
        edge.put("e12cd", edge2);
        edge.put("e12ccd", edge11);

        LocalTime time1 = LocalTime.now();
//        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
//        ArrayList<Double> lim = new ArrayList<>();
//        ArrayList<Integer> sin = new ArrayList<>();
//        ArrayList<Double> min = new ArrayList<>();
//        ArrayList<Double> max = new ArrayList<>();
//        ArrayList<Double> goal = new ArrayList<>();
//        ArrayList<Boolean> type = new ArrayList<>();
//        goal.add(0.0);
//
//        first.forEach(f -> {
//            ArrayList<Double> row = new ArrayList<>();
//            row.add(0.0);
//            second.forEach(s -> {
//                if (node.containsKey(f)) {
//                    double v = 0;
//                    NodeData n = node.get(f);
//                    String nodeType = n.getNodeType();
//
//                    switch (s) {
//                        case "sin":
//                            v = (!nodeType.equals("source")) ? 3 : 1;
//                            sin.add((int) v);
//                            break;
//                        case "lim":
//                            v = (!nodeType.equals("source")) ? -n.getLoad() : 0;
//                            lim.add(v);
//                            break;
//                        default:
//                            EdgeData e = edge.get(s);
//                            boolean isSource = f.equals(e.getSource());
//                            boolean isTarget = f.equals(e.getTarget());
//                            if (isSource) {
//                                v = s.endsWith("d") ? -n.getMaxGen() : 1;
//                            } else if (isTarget) {
//                                v = s.endsWith("d") ? 0 : -1;
//                            }
//                            row.add(v);
//                            break;
//                    }
//                } else {
//                    switch (s) {
//                        case "sin":
//                            lim.add(1.0);
//                            break;
//                        case "lim":
//                            sin.add(1);
//                            break;
//                        default:
//                            row.add(s.endsWith("d") ? 1.0 : 0.0);
//                            break;
//                    }
//                }
//            });
//            matrix.add(row);
//        });
//
//        second.remove(second.size() - 1);
//        second.remove(second.size() - 1);
//        first.remove(first.size() - 1);
//
//        second.forEach(s -> {
//            first.forEach(t -> {
//                NodeData n = node.get(t);
//                EdgeData e = edge.get(s);
//                boolean isSource = t.equals(e.getSource());
//                boolean isTarget = t.equals(e.getTarget());
//                String nodeType = n.getNodeType();
//
//                if (isSource) {
//                    if (s.endsWith("d")) {
//                        min.add(0.0);
//                        max.add(1.0);
//                        goal.add((double) n.getCost());
//                        type.add(true);
//                    } else if (nodeType.equals("source")) {
//                        min.add((double) n.getMinGen());
//                        max.add((double) n.getMaxGen());
//                        goal.add((double) n.getPrice());
//                        type.add(false);
//                    }
//                } else if (isTarget && nodeType.equals("consumer")) {
//                    min.add((double) n.getMinGen());
//                    max.add((double) n.getLoad());
//                    goal.add((double) n.getCost());
//                    type.add(false);
//                }
//            });
//        });


        ObjectMapper objectMapper = new ObjectMapper();
        Problem problem = new Problem(first, second, node, edge);
        MathStatement mathStatement = LinearStatement.getLinearStatement(problem);
        Solution solution = optimate(mathStatement);// вызываем функцию оптимизации
//        System.out.println(getObjective());// выводим значение целевой функции
//        System.out.println(Arrays.toString(getVariables()));// выводим значения переменных
        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);
        System.out.println(duration.toMillis() + " миллисекунд");
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String str = objectMapper.writeValueAsString(problem);
        System.out.println(str);

    }
}