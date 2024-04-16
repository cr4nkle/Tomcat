package program.logic;
import lpsolve.*;

public class Optimator {
    private static LpSolve problem;//создаём экземпляр решателя

    /**
     * @f вектор коэффициентов целевой функции;
     *  @m матрица коэффициентов;
     * @sin вектор знаков неравенств;
     * @b вектор ограничений правой части;
     * @min вектор ограничений по минимальному значению;
     * @max вектор ограничений по максимальному значению;
    **/
    public static void optimate(double[] f, double[][] m, int[] sin,
                                double[] b, double[] min, double[] max) throws LpSolveException{

        problem = LpSolve.makeLp(0, f.length - 1);//указываем количество ограничений и длину целевой функции
        problem.setObjFn(f);//задаём целевую функцию

        for(int i = 1; i < f.length; i++) {
            problem.setInt(i, false);
        }

        if(m != null){
            //ограничения в виде неравенств
            for(int i = 0; i < m.length; i++){
                problem.addConstraint(m[i], sin[i], b[i]);
            }
        }

        //накладываем ограничения на переменные
        for(int i = 0; i < min.length; i++){
            problem.setUpbo(i + 1, max[i]);
            problem.setLowbo(i + 1, min[i]);
        }

        problem.printLp();//печатаем постановку задачи
        problem.solve();//вызываем функцию, которая решает задачу

    }

    //функция возвращает значение целевой функции
    public static double getObjective() throws LpSolveException{
        if(problem != null){
            return problem.getWorkingObjective();
        }else{
            return 0;
        }
    }

    //функция возвращает вектор переменных
    public static double[] getVariables() throws LpSolveException{
        if(problem != null){
            return problem.getPtrVariables();
        }else{
            return null;
        }
    }}
