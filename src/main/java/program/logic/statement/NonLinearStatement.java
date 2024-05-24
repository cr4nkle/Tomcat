package program.logic.statement;

import program.model.graph.EdgeData;
import program.model.graph.NodeData;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class NonLinearStatement {
    public static MathStatement getNonLinearStatement(Problem problem) {
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
        AtomicInteger count = new AtomicInteger();
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

                        //то что с правой части в матрице
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
                                //тело самой матрицы
                                EdgeData e = edge.get(s);
                                boolean isSource = f.equals(e.getSource());
                                boolean isTarget = f.equals(e.getTarget());

                                if (isSource) {
                                    v = s.endsWith("d") ? -n.getMaxGen() : 1;
                                } else if (isTarget) {
                                    if (nodeType.equals("source")) {
                                        v = s.endsWith("d") ? -n.getMaxGen() : -1;
                                    } else {
                                        v = s.endsWith("d") ? 0 : -1;
                                    }
                                }
                                row.add(v);
                                break;
                        }
                    } else {
                        //для последней строки
                        switch (s) {
                            case "sign":
                                lim.add(1.0);
                                break;
                            case "lim":
                                sign.add(1);
                                break;
                            default:
                                EdgeData e = edge.get(s);
                                row.add(!e.isInstalled() ? 1.0 : 0.0);
                                break;
                        }
                    }
                });
                matrix.add(row);
            });
        });

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            secondCopy.forEach(s -> {
                EdgeData e = edge.get(s);
                min.add(0.0);
                max.add((double) e.getThroughput());


                if (!e.isInstalled()) {
                    goal.add((double) e.getCost());
                    type.add(true);
                    count.getAndIncrement();
                } else {
                    goal.add(0.0);
                    type.add(false);
                }
            });
        });

        task1.join();
        task2.join();
        MathStatement mathStatement = new MathStatement(lim, max, min, goal, matrix, sign, type, count.get());
        String str = convertModel(mathStatement);
        System.out.println(str);
        return mathStatement;
    }

    public static String convertModel(MathStatement mathStatement) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ArrayList<Double>> matrix = mathStatement.getMatrix();
        ArrayList<Double> lim = mathStatement.getLim();
        ArrayList<Integer> sign = mathStatement.getSign();
        ArrayList<Double> min = mathStatement.getMin();
        ArrayList<Double> max = mathStatement.getMax();
        ArrayList<Double> goal = mathStatement.getGoal();
        ArrayList<Boolean> type = mathStatement.getType();
        int count = mathStatement.getCount();

        int rows = matrix.size();
        int cols = matrix.get(0).size() - 1;

        sb.append(String.format("\nparam cost{i in 1..%d};\n", cols));
        sb.append(String.format("param coef{i in 1..%d, j in 1..%d};\n\n", rows, cols));

        sb.append(String.format("var x{i in 1..%d};\n", cols));
        sb.append(String.format("var bin{i in 1..%d} binary;\n\n", count));
        sb.append(String.format("minimize sum_costs: (sum{i in 1..%d} x[i]*cost[i]);\n\n", cols));

        for (int i = 0; i < matrix.size(); i++) {
            int k = i + 1;
            String s = "";
            Double l = lim.get(i);

            if (sign.get(i) == 1) {
                s = "<=";
            } else if (sign.get(i) == 3) {
                s = "=";
            }

            sb.append(String.format(
                    "subject to R%d: sum{i in %d..%d, j in 1..%d} coef[i, j]*x[j]%s%d;\n",
                    k, k, k, cols, s, l.intValue()));
        }

        sb.append("\nsubject to limits:");
        for (int i = 0; i < count; i++) {
            sb.append(String.format("bin[%d]", i + 1));
            if (i != count - 1)
                sb.append("+");
        }
        sb.append("=1;\n\n");

        sb.append(String.format("subject to x_c {i in 1..%d}: 0<=x[i];", cols));
        return sb.toString();
    }

    public static String convertData(MathStatement mathStatement) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ArrayList<Double>> matrix = mathStatement.getMatrix();
        ArrayList<Double> lim = mathStatement.getLim();
        ArrayList<Integer> sign = mathStatement.getSign();
        ArrayList<Double> min = mathStatement.getMin();
        ArrayList<Double> max = mathStatement.getMax();
        ArrayList<Double> goal = mathStatement.getGoal();
        ArrayList<Boolean> type = mathStatement.getType();
        int count = mathStatement.getCount();

        // Заполняем строку для cost
        sb.append("param cost:= ");
        for (int i = 0; i < goal.size(); i++) {
            if (i != 0) {
                if (i != goal.size() - 1) {
                    sb.append(i).append(" ").append(goal.get(i)).append("\t");
                } else {
                    sb.append(i).append(" ").append(goal.get(i)).append(";");
                }
            }
        }

        // Заполняем строку для coef
        sb.append("\nparam coef:\t");
        for (int i = 1; i < matrix.get(0).size(); i++) {
            if (i == matrix.get(0).size() - 1) {
                sb.append(i).append(":=\n\t");
            } else {
                sb.append(i).append("\t");
            }
        }

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                Double value = matrix.get(i).get(j);
                if (j == 0) {
                    sb.append(i + 1).append("\t");
                } else {
                    sb.append(value.intValue()).append("\t");
                }
            }
            if (i < matrix.size() - 1)
                sb.append("\n\t");

            if (i == matrix.size() - 1)
                sb.append(";");
        }
        return sb.toString();
    }

}
