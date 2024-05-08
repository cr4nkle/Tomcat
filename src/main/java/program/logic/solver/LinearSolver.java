package program.logic.solver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lpsolve.*;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Solution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LinearSolver {
    private static LpSolve problem;// создаём экземпляр решателя

    public static Solution optimate(MathStatement mathStatement) {
        ObjectMapper objectMapper = new ObjectMapper();
        double[][] matrix = objectMapper.convertValue(mathStatement.getMatrix(), double[][].class);
        double[] goal = objectMapper.convertValue(mathStatement.getGoal(), double[].class);
        double[] lim = objectMapper.convertValue(mathStatement.getLim(), double[].class);
        double[] min = objectMapper.convertValue(mathStatement.getMin(), double[].class);
        double[] max = objectMapper.convertValue(mathStatement.getMax(), double[].class);
        int[] sign = objectMapper.convertValue(mathStatement.getSign(), int[].class);
        boolean[] type = objectMapper.convertValue(mathStatement.getType(), boolean[].class);

        try {
            problem = LpSolve.makeLp(0, goal.length - 1);// указываем количество ограничений и длину целевой функции
            problem.setObjFn(goal);// задаём целевую функцию

            int i = 1;
            for (boolean b : type) {
                problem.setInt(i++, b);
            }

            if (matrix != null) {
                // ограничения в виде неравенств
                for (int j = 0; j < matrix.length; j++) {
                    problem.addConstraint(matrix[j], sign[j], lim[j]);
                }
            }

            // накладываем ограничения на переменные
            i = 1;
            for (double mn : min) {
                problem.setLowbo(i++, mn);
            }

            i = 1;
            for (double mx : max) {
                problem.setUpbo(i++, mx);
            }

            problem.solve();// вызываем функцию, которая решает задачу
            double objective = problem.getWorkingObjective();
            double[] variables = problem.getPtrVariables();
            deleteProblem();

            return new Solution(mathStatement, objective, variables);
        } catch (LpSolveException e) {
            throw new RuntimeException(e);
        }

    }

    // функция возвращает значение целевой функции
    public static double getObjective() {
        if (problem != null) {
            try {
                return problem.getWorkingObjective();
            } catch (LpSolveException e) {
                throw new RuntimeException(e);
            }
        } else {
            return 0;
        }
    }

    // функция возвращает вектор переменных
    public static double[] getVariables() {
        if (problem != null) {
            try {
                return problem.getPtrVariables();
            } catch (LpSolveException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    private static void deleteProblem() {
        if (problem != null) {
            problem.deleteLp();
            problem = null; // Устанавливаем ссылку на null, чтобы предотвратить повторное использование
        }
    }
}
