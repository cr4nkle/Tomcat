package program;
import lpsolve.*;
import org.example.JsonReader;

import java.util.Arrays;

import static program.logic.Optimator.*;

public class Main {
    public static void main(String[] args) {
        try {
//            PostgresHelper postgresHelper = new PostgresHelper();
//            postgresHelper.readFromDb();
//            try (var greeter = new Neo4jHelper("bolt://localhost:7687", "neo4j", "rootroot")) {
//                greeter.write();
//                greeter.read();
//            }
            JsonReader jsonReader = new JsonReader();
//            jsonReader.readJson(new Object());
            //в 0 положения поставил 0 так как библиотека берёт с 1-го элемента
            double[] f = {0, 200, 300, 0, 0};//коэффициенты целевой функции
            double [][] m = {
                    {0, 1, 1, 0, 0},
                    {0, 1, 0, -10, 0},
                    {0, 0, 1, 0, -20},
                    {0, 0, 0, 1, 1},

            };//матрица коэффициентов
            int[] sin = {3, 1, 1, 1};//вектор знаков 1 это <=, 3 это =
            double[] b = {7, 0, 0, 1};//вектор ограничений правой части
            double[] min = {0, 0, 0, 0};//ограничения по минимальному значению
            double[] max = {10, 20, 0, 0};//ограничения по максимальному значению

            optimate(f, m, sin, b, min, max);//вызываем функцию оптимизации
            System.out.println(getObjective());//выводим значение целевой функции
            System.out.println(Arrays.toString(getVariables()));//выводим значения переменных
        } catch (LpSolveException e) {
            e.printStackTrace();
        }
    }
}