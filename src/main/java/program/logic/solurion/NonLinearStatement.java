package program.logic.solurion;

import program.model.compressedGraph.EdgeData;
import program.model.compressedGraph.NodeData;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class NonLinearStatement {
    public static MathStatement getNonLinearStatement(Problem problem) {
        System.out.println(problem.toString());
        ArrayList<String> first = problem.getNodeId();
        ArrayList<String> second = problem.getEdgeId();
        ArrayList<String> firstCopy = new ArrayList<>(first);
        ArrayList<String> secondCopy = new ArrayList<>(second);
        HashMap<String, NodeData> node = problem.getNode();
        HashMap<String, EdgeData> edge = problem.getEdge();

        secondCopy.remove(secondCopy.size() - 1);
        secondCopy.remove(secondCopy.size() - 1);
        firstCopy.remove(firstCopy.size() - 1);

        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        ArrayList<Double> lim = new ArrayList<>();
        ArrayList<Integer> sign = new ArrayList<>();
        ArrayList<Double> min = new ArrayList<>();
        ArrayList<Double> max = new ArrayList<>();
        ArrayList<Double> goal = new ArrayList<>();
        ArrayList<Boolean> type = new ArrayList<>();

        goal.add(0.0);//добавляем 0, чтобы выровнять нумерацию в решателе

        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            first.forEach(f -> {
                ArrayList<Double> row = new ArrayList<>();
                row.add(0.0);
                second.forEach(s -> {
                    if (node.containsKey(f)) {
                        double v = 0;
                        NodeData n = node.get(f);
                        String nodeType = n.getNodeType();
                        float h = 1.0f;

                        switch (s) {
                            case "sign":
                                v = (!nodeType.equals("source")) ? 3 : 1;
                                sign.add((int) v);
                                break;
                            case "lim":
                                v = (!nodeType.equals("source")) ? -n.getLoad() : 0;
                                lim.add(v);
                                break;
                            default:
                                EdgeData e = edge.get(s);
                                boolean isSource = f.equals(e.getSource());
                                boolean isTarget = f.equals(e.getTarget());
                                if (isSource) {
                                    v = s.endsWith("d") ? -n.getMaxGen() : 1;
                                    System.out.println(n.getMaxGen());
                                } else if (isTarget) {
                                    if (nodeType.equals("source")) {
                                        h = n.getEfficiency();
                                        BigDecimal bd = new BigDecimal(Float.toString(-1 * h));
                                        BigDecimal rounded = bd.setScale(2, RoundingMode.HALF_UP);
                                        double doubleValue = rounded.doubleValue();
                                        v = s.endsWith("d") ? 0 : doubleValue;
                                    } else {
                                        v = s.endsWith("d") ? 0 : -1;
                                    }

                                }
                                row.add(v);
                                break;
                        }
                    } else {
                        switch (s) {
                            case "sign":
                                lim.add(1.0);
                                break;
                            case "lim":
                                sign.add(1);
                                break;
                            default:
                                row.add(s.endsWith("d") ? 1.0 : 0.0);
                                break;
                        }
                    }
                });
                matrix.add(row);
            });
        });

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            secondCopy.forEach(s -> {
                firstCopy.forEach(t -> {
                    NodeData n = node.get(t);
                    EdgeData e = edge.get(s);
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
        });

        task1.join();
        task2.join();

        return new MathStatement(lim, max, min, goal, matrix, sign, type);
    }
}
