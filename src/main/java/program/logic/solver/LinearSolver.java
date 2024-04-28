package program.logic.solver;

import lpsolve.*;

public class LinearSolver {
    private static LpSolve problem;// создаём экземпляр решателя

    /**
     * @f вектор коэффициентов целевой функции;
     * @m матрица коэффициентов;
     * @sin вектор знаков неравенств;
     * @b вектор ограничений правой части;
     * @min вектор ограничений по минимальному значению;
     * @max вектор ограничений по максимальному значению;
     **/
    public static void optimate(
            double[] goal,
            double[][] matrix,
            int[] sin,
            double[] lim,
            double[] min,
            double[] max,
            boolean[] type) throws LpSolveException {

        problem = LpSolve.makeLp(0, goal.length - 1);// указываем количество ограничений и длину целевой функции
        problem.setObjFn(goal);// задаём целевую функцию

        int i = 1;
        for (boolean b : type){
            problem.setInt(i++, b);
        }

        if (matrix != null) {
            // ограничения в виде неравенств
            for (int j = 0; j < matrix.length; j++) {
                problem.addConstraint(matrix[j], sin[j], lim[j]);
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

        problem.printLp();// печатаем постановку задачи, потом убрать можно
        problem.solve();// вызываем функцию, которая решает задачу

    }

    // функция возвращает значение целевой функции
    public static double getObjective() throws LpSolveException {
        if (problem != null) {
            return problem.getWorkingObjective();
        } else {
            return 0;
        }
    }

    // функция возвращает вектор переменных
    public static double[] getVariables() throws LpSolveException {
        if (problem != null) {
            return problem.getPtrVariables();
        } else {
            return null;
        }
    }
}
