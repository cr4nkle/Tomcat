package program;

import com.fasterxml.jackson.databind.SerializationFeature;
import program.logic.neos.NeosClient;
import program.logic.solver.LinearSolver;
import program.logic.statement.LinearStatement;
import program.logic.statement.NonLinearStatement;
import program.logic.xml.XmlGenerator;
import program.model.graph.EdgeData;
import program.model.graph.NodeData;
import program.model.locale.ModalConfig;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;
import program.model.mathStatement.Solution;
import program.utils.Constant;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        first.add("n1");
        first.add("n2");
        first.add("n3");
        first.add("n4");
        // first.add("lim");

        second.add("e12");
        second.add("e12c");
        second.add("e12cc");
        second.add("e12ccd");
        second.add("e23");
        second.add("e24");
        second.add("sign");
        second.add("lim");

        NodeData node1 = new NodeData("source", 2, 0, 10, 0, 0, "heat", true, 0.95f);
        NodeData node3 = new NodeData("consumer", 0, 0, 0, 0, 3, "heat", true, 0.95f);
        NodeData node4 = new NodeData("consumer", 0, 0, 0, 0, 4, "heat", true, 0.95f);
        NodeData node2 = new NodeData("connector", 0, 0, 0, 0, 0, "heat", true, 0.95f);
        NodeData node5 = new NodeData("consumer", 0, 0, 0, 0, 5, "heat", true, 0.95f);

        EdgeData edge1 = new EdgeData("heat", 6, 0, 10, 10.0, "n1", "n2", false);
        EdgeData edge3 = new EdgeData("heat", 3, 0, 0, 14.0, "n2", "n3", true);
        EdgeData edge4 = new EdgeData("heat", 4, 0, 0, 17.0, "n2", "n4", true);
        EdgeData edge5 = new EdgeData("heat", 10, 0, 20, 17.0, "n1", "n2", false);
        EdgeData edge6 = new EdgeData("heat", 10, 0, 50, 17.0, "n1", "n2", false);
        EdgeData edge7 = new EdgeData("heat", 10, 0, 50, 17.0, "n1", "n2", false);

        HashMap<String, NodeData> node = new HashMap<>();
        HashMap<String, EdgeData> edge = new HashMap<>();
        node.put("n1", node1);
        node.put("n2", node2);
        node.put("n3", node3);
        node.put("n4", node4);
        node.put("n5", node5);

        edge.put("e12", edge1);
        edge.put("e12c", edge5);
        edge.put("e12cc", edge6);
        edge.put("e12ccd", edge7);
        edge.put("e23", edge3);
        edge.put("e24", edge4);

        LocalTime time1 = LocalTime.now();

        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        ArrayList<Double> lim = new ArrayList<>();
        ArrayList<Integer> sign = new ArrayList<>();
        ArrayList<Double> min = new ArrayList<>();
        ArrayList<Double> max = new ArrayList<>();
        ArrayList<Double> goal = new ArrayList<>();
        ArrayList<Boolean> type = new ArrayList<>();

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
                                if (!nodeType.equals("source")) {
                                    v = -n.getLoad();
                                } else if (nodeType.equals("source") && n.isInstalled()) {
                                    v = n.getMaxGen();
                                } else {
                                    v = 0;
                                }
                                lim.add(v);
                                break;
                            default:
                                EdgeData e = edge.get(s);
                                boolean isSource = f.equals(e.getSource());
                                boolean isTarget = f.equals(e.getTarget());
                                if (isSource) {
                                    v = s.endsWith("d") ? -n.getMaxGen() : 1;
                                } else if (isTarget) {
                                    if (nodeType.equals("source")) {
                                        h = n.getEfficiency();
                                        BigDecimal bd = new BigDecimal(Float.toString(-1 * h));
                                        BigDecimal rounded = bd.setScale(2, RoundingMode.HALF_UP);
                                        double doubleValue = rounded.doubleValue();
                                        v = s.endsWith("d") ? -n.getMaxGen() : doubleValue;
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
        second.remove(second.size() - 1);
        second.remove(second.size() - 1);
        first.remove(first.size() - 1);

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            second.forEach(s -> {
                EdgeData e = edge.get(s);
                AtomicBoolean isInstalled = new AtomicBoolean(false);

                first.forEach(t -> {
                    NodeData n = node.get(t);

                    boolean isSource = t.equals(e.getSource());
                    boolean isTarget = t.equals(e.getTarget());
                    String nodeType = n.getNodeType();
                    // System.out.println(nodeType);
                    if (isSource) {
                        if (s.endsWith("d")) {
                            min.add(0.0);
                            max.add(1.0);
                            type.add(true);
                            goal.add((double) n.getCost());
                            isInstalled.set(true);
                        } else if (nodeType.equals("source")) {
                            goal.add((double) n.getPrice());
                            type.add(false);
                        }
                    } else if (isTarget) {
                        if (nodeType.equals("consumer")) {
                            goal.add((double) n.getCost());
                            type.add(false);
                        } else if (nodeType.equals("source") && !s.endsWith("d")) {
                            goal.add((double) n.getPrice());
                            type.add(false);
                        }
                    }

                });

                if (!isInstalled.get()) {
                    min.add(0.0);
                    max.add((double) e.getThroughput());
                }
            });
        });

        task1.join();
        task2.join();

        LocalTime time2 = LocalTime.now();
        Duration duration = Duration.between(time1, time2);
        System.out.println(duration.toMillis() + " миллисекунд");
        // Problem problem = new Problem();
        // problem.setNodeId(first);
        // problem.setEdgeId(second);
        // problem.setEdge(edge);
        // problem.setNode(node);

        // ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // String str = objectMapper.writeValueAsString(problem);
        // System.out.println(str);

        // MathStatement mathStatement =
        // NonLinearStatement.getNonLinearStatement(problem);
        // str = objectMapper.writeValueAsString(mathStatement);
        // System.out.println(str);

        // Solution solution = LinearSolver.getInstance().optimate(mathStatement);
        // str = objectMapper.writeValueAsString(solution);
        // System.out.println(str);
        // String model = NonLinearStatement.convertModel(mathStatement);
        // String data = NonLinearStatement.convertData(mathStatement);
        // XmlGenerator xmlGenerator = new XmlGenerator();

        // xmlGenerator.generate(model, data);
        // NeosClient.getInstance().submitProblem("C:/Users/cr4nk/IdeaProjects/Tomcat/output.xml");
    }
}