package program;

import com.fasterxml.jackson.databind.ObjectMapper;
import lpsolve.LpSolveException;
import program.model.compressedGraph.CalcEdge;
import program.model.compressedGraph.CalcNode;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static program.logic.Optimator.*;


public class Main {
    public static void main(String[] args) {


        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        first.add("n1");
        first.add("n1c");
        first.add("n2");
        first.add("n3");
        first.add("n4");
        first.add("lim");

        second.add("e12");
        second.add("e12c");
        second.add("e23");
        second.add("e24");
        second.add("e12d");
        second.add("e12cd");
        second.add("sin");
        second.add("lim");

        CalcNode node1 = new CalcNode("source", 2, 20, 5, 0, 0, "heat", true);
        CalcNode node2 = new CalcNode("source", 3, 30, 6, 0, 0, "heat", true);
        CalcNode node3 = new CalcNode("consumer", 0, 0, 0, 0, 2, "heat", false);
        CalcNode node4 = new CalcNode("consumer", 0, 0, 0, 0, 3, "heat", false);
        CalcNode node5 = new CalcNode("connector", 0, 0, 0, 0, 0, "heat", false);
        CalcEdge edge1 = new CalcEdge("heat", 2, 5, 0, 0, 0, 0, 0, "n1", "n2");
        CalcEdge edge2 = new CalcEdge("heat", 2, 6, 0, 0, 0, 0, 0, "n1c", "n2");
        CalcEdge edge3 = new CalcEdge("heat", 2, 3, 0, 0, 0, 0, 0, "n2", "n3");
        CalcEdge edge4 = new CalcEdge("heat", 2, 4, 0, 0, 0, 0, 0, "n2", "n4");
        HashMap<String, CalcNode> node = new HashMap<>();
        HashMap<String, CalcEdge> edge = new HashMap<>();
        node.put("n1", node1);
        node.put("n1c", node2);
        node.put("n2", node5);
        node.put("n3", node3);
        node.put("n4", node4);

        edge.put("e12", edge1);
        edge.put("e12c", edge2);
        edge.put("e23", edge3);
        edge.put("e24", edge4);
        edge.put("e12d", edge1);
        edge.put("e12cd", edge2);

        LocalTime time1 = LocalTime.now();
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        ArrayList<Double> lim = new ArrayList<>();
        ArrayList<Integer> sin = new ArrayList<>();
        ArrayList<Double> min = new ArrayList<>();
        ArrayList<Double> max = new ArrayList<>();
        ArrayList<Double> goal = new ArrayList<>();
        ArrayList<Boolean> type = new ArrayList<>();
        goal.add(0.0);

        first.forEach(f -> {
            ArrayList<Double> row = new ArrayList<>();
            row.add(0.0);
            second.forEach(s -> {
                if (node.containsKey(f)) {
                    double v = 0;
                    CalcNode n = node.get(f);
                    String nodeType = n.getNodeType();

                    switch (s) {
                        case "sin":
                            v = (!nodeType.equals("source")) ? 3 : 1;
                            sin.add((int) v);
                            break;
                        case "lim":
                            v = (!nodeType.equals("source")) ? -n.getLoad() : 0;
                            lim.add(v);
                            break;
                        default:
                            CalcEdge e = edge.get(s);
                            boolean isSource = f.equals(e.getSource());
                            boolean isTarget = f.equals(e.getTarget());
                            if (isSource) {
                                v = s.endsWith("d") ? -n.getMaxGen() : 1;
                            } else if (isTarget) {
                                v = s.endsWith("d") ? 0 : -1;
                            }
                            row.add(v);
                            break;
                    }
                } else {
                    switch (s) {
                        case "sin":
                            lim.add(1.0);
                            break;
                        case "lim":
                            sin.add(1);
                            break;
                        default:
                            row.add(s.endsWith("d") ? 1.0 : 0.0);
                            break;
                    }
                }
            });
            matrix.add(row);
        });

        second.remove(second.size() - 1);
        second.remove(second.size() - 1);
        first.remove(first.size() - 1);

        second.forEach(s -> {
            first.forEach(t -> {
                CalcNode n = node.get(t);
                CalcEdge e = edge.get(s);
                boolean isSource = t.equals(e.getSource());
                boolean isTarget = t.equals(e.getTarget());
                String nodeType = n.getNodeType();

                if (isSource) {
                    if (s.endsWith("d")) {
                        min.add(0.0);
                        max.add(1.0);
                        goal.add((double) n.getCost());
                        type.add(true);
                    } else if (nodeType.equals("source")) {
                        min.add((double) n.getMinGen());
                        max.add((double) n.getMaxGen());
                        goal.add((double) n.getPrice());
                        type.add(false);
                    }
                } else if (isTarget && nodeType.equals("consumer")) {
                    min.add((double) n.getMinGen());
                    max.add((double) n.getLoad());
                    goal.add((double) n.getCost());
                    type.add(false);
                }
            });
        });

//        LocalTime time2 = LocalTime.now();
//
//        System.out.println();
//        Duration duration = Duration.between(time1, time2);
//        System.out.println(duration.toMillis() + " миллисекунд");

//        matrix.forEach(row -> {
//            row.forEach(x -> {
//                System.out.print(x + " ");
//            });
//            System.out.println();
//        });
//        sin.forEach(s -> {
//            System.out.print(s + " ");
//        });
//        System.out.println();
//        lim.forEach(x -> {
//            System.out.print(x + " ");
//        });
//        System.out.println();
//        min.forEach(x -> {
//            System.out.print(x + " ");
//        });
//        System.out.println();
//        max.forEach(x -> {
//            System.out.print(x + " ");
//        });
//        System.out.println();
//        goal.forEach(x -> {
//            System.out.print(x + " ");
//        });
//        System.out.println();


        try {
//            double[] f = { 0, 2, 3, 0, 0, 20, 30 };// коэффициенты целевой функции
//            double[][] m = {
//                { 0, 1, 0, 0, 0, -5, 0 },
//                { 0, 0, 1, 0, 0, 0, -6 },
//                { 0, -1, -1, 1, 1, 0, 0 },
//                { 0, 0, 0, -1, 0, 0, 0 },
//                { 0, 0, 0, 0, -1, 0, 0 },
//                { 0, 0, 0, 0, 0, 1, 1},
//
//            };// матрица коэффициентов
//            int[] sin = { 1, 1, 3, 3, 3, 1 };// вектор знаков 1 это <=, 3 это =
//            double[] b = { 0, 0, 0, -2, -3, 1 };// вектор ограничений правой части
//            double[] min = { 0, 0, 0, 0, 0, 0 };// ограничения по минимальному значению
//            double[] max = { 5, 6, 2, 3, 1, 1};// ограничения по максимальному значению
            ObjectMapper objectMapper = new ObjectMapper();
            double[][] matrixC = objectMapper.convertValue(matrix, double[][].class);
            double[] goalC = objectMapper.convertValue(goal, double[].class);
            double[] limC = objectMapper.convertValue(lim, double[].class);
            double[] minC = objectMapper.convertValue(min, double[].class);
            double[] maxC = objectMapper.convertValue(max, double[].class);
            int[] sinC = objectMapper.convertValue(sin, int[].class);
            boolean[] typeC = objectMapper.convertValue(type, boolean[].class);
            optimate(goalC, matrixC, sinC, limC, minC, maxC, typeC);// вызываем функцию оптимизации
            System.out.println(getObjective());// выводим значение целевой функции
            System.out.println(Arrays.toString(getVariables()));// выводим значения переменных
        } catch (LpSolveException e) {
            e.printStackTrace();
        }
        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);
        System.out.println(duration.toMillis() + " миллисекунд");
    }
}