package program.logic.solver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lpsolve.*;
import program.model.mathStatement.MathStatement;
import program.model.mathStatement.Solution;

public class LinearSolver {
    private static volatile LinearSolver INSTANCE;

    private LinearSolver() {
    }

    public static LinearSolver getInstance() {
        System.out.println("Делаем солвер");
        if (INSTANCE == null) {
            synchronized (LinearSolver.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LinearSolver();
                }
            }
        }
        return INSTANCE;
    }

    public Solution optimate(MathStatement mathStatement) {
        System.out.println("Решаем");
        ObjectMapper objectMapper = new ObjectMapper();
        double[][] matrix = objectMapper.convertValue(mathStatement.getMatrix(), double[][].class);
        double[] goal = objectMapper.convertValue(mathStatement.getGoal(), double[].class);
        double[] lim = objectMapper.convertValue(mathStatement.getLim(), double[].class);
        double[] min = objectMapper.convertValue(mathStatement.getMin(), double[].class);
        double[] max = objectMapper.convertValue(mathStatement.getMax(), double[].class);
        int[] sign = objectMapper.convertValue(mathStatement.getSign(), int[].class);
        boolean[] type = objectMapper.convertValue(mathStatement.getType(), boolean[].class);
        double objective = 0.0;
        double[] variables = null;

        System.out.println("Объявили переменные");
        try {
            // тут падает
            LpSolve problem = LpSolve.makeLp(0, goal.length - 1);// указываем количество ограничений и длину целевой
                                                                 // функции
            problem.setObjFn(goal);// задаём целевую функцию
            System.out.println("Задали целевую");
            int i = 1;
            for (boolean b : type) {
                problem.setInt(i++, b);
            }
            System.out.println("1");
            if (matrix != null) {
                // ограничения в виде неравенств
                for (int j = 0; j < matrix.length; j++) {
                    problem.addConstraint(matrix[j], sign[j], lim[j]);
                }
            }
            System.out.println("2");
            // накладываем ограничения на переменные
            i = 1;
            for (double mn : min) {
                problem.setLowbo(i++, mn);
            }
            System.out.println("3");
            i = 1;
            for (double mx : max) {
                problem.setUpbo(i++, mx);
            }

            problem.solve();
            System.out.println("Решили");
            objective = getObjective(problem);
            variables = getVariables(problem);
            System.out.println("Нашли переменные");
            deleteProblem(problem);
            System.out.println("Задали целевую");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println("Возвращаем");
        Solution solution = new Solution(mathStatement, objective, variables);
        System.out.println(solution.getObjective());
        return solution;
    }

    // функция возвращает значение целевой функции
    public double getObjective(LpSolve problem) {
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
    public double[] getVariables(LpSolve problem) {
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

    private static void deleteProblem(LpSolve problem) {
        if (problem != null) {
            problem.deleteLp();
            problem = null; // Устанавливаем ссылку на null, чтобы предотвратить повторное использование
        }
    }
}
