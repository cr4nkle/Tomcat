package program.logic.statement;

import program.model.graph.EdgeData;
import program.model.graph.NodeData;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.NonLinearSolution;
import program.model.mathStatement.Problem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class NonLinearStatement {

    public static NonLinearSolution getSolution(Problem problem) {
        MathStatement mathStatement = getNonLinearStatement(problem);
        return new NonLinearSolution(
                convertData(mathStatement),
                convertModel(mathStatement),
                convertRun(),
                mathStatement);
    }

    private static MathStatement getNonLinearStatement(Problem problem) {
        ArrayList<String> first = problem.getNodeId();
        ArrayList<String> second = problem.getEdgeId();
        ArrayList<String> secondCopy = new ArrayList<>(second);
        HashMap<String, NodeData> node = problem.getNode();
        HashMap<String, EdgeData> edge = problem.getEdge();

        secondCopy.remove(secondCopy.size() - 1);
        secondCopy.remove(secondCopy.size() - 1);
        first.remove(first.size() - 1);

        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();
        ArrayList<Double> lim = new ArrayList<>();
        ArrayList<Integer> sign = new ArrayList<>();
        ArrayList<Double> min = new ArrayList<>();
        ArrayList<Double> max = new ArrayList<>();
        ArrayList<Double> goal = new ArrayList<>();
        ArrayList<Boolean> type = new ArrayList<>();
        AtomicInteger count = new AtomicInteger();
        goal.add(0.0);// добавляем 0, чтобы выровнять нумерацию в решателе

        first.forEach(f -> {
            ArrayList<Double> row = new ArrayList<>();
            row.add(0.0);

            second.forEach(s -> {
                if (node.containsKey(f)) {
                    double v = 0;
                    NodeData n = node.get(f);
                    String nodeType = n.getNodeType();

                    // то что с правой части в матрице
                    switch (s) {
                        case "sign":
                            sign.add(!nodeType.equals("source") ? 3 : 1);
                            break;
                        case "lim":
                            if (!nodeType.equals("source")) {
                                v = -n.getLoad();
                            } else if (nodeType.equals("source")) {
                                v = n.getMaxGen();
                            } else {
                                v = 0;
                            }
                            lim.add(v);
                            break;
                        default:
                            // тело самой матрицы
                            EdgeData e = edge.get(s);
                            boolean isSource = f.equals(e.getSource());
                            boolean isTarget = f.equals(e.getTarget());

                            if (isSource) {
                                row.add(1.0);
                            } else if (isTarget) {
                                row.add(-1.0);
                            } else {
                                row.add(0.0);
                            }
                            break;
                    }
                }
            });
            matrix.add(row);
        });

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

        return new MathStatement(lim, max, min, goal, matrix, sign, type, count.get());
    }

    private static String convertModel(MathStatement mathStatement) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ArrayList<Double>> matrix = mathStatement.getMatrix();
        ArrayList<Double> lim = mathStatement.getLim();
        ArrayList<Integer> sign = mathStatement.getSign();
        int count = mathStatement.getCount();

        int rows = matrix.size();
        int cols = matrix.get(0).size() - 1;

        sb.append(String.format("param cost{i in 1..%d};\n", cols));
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

    private static String convertData(MathStatement mathStatement) {
        StringBuilder sb = new StringBuilder();
        ArrayList<ArrayList<Double>> matrix = mathStatement.getMatrix();
        ArrayList<Double> goal = mathStatement.getGoal();

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

    private static String convertRun() {
        return "solve;\ndisplay x;";
    }

}
