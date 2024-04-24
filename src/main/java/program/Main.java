package program;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lpsolve.*;
import org.example.JsonReader;
import program.logic.calculate.NodeSolution;
import program.model.graph.Graph;
import program.model.mathStatement.MathStatement;

import java.io.IOException;
import java.util.Arrays;

import static program.logic.Optimator.*;

public class Main {
    public static void main(String[] args) {
        try {
            double[] f = { 0, 2, 3, 0, 0, 20, 30 };// коэффициенты целевой функции
            double[][] m = {
                { 0, 1, 0, 0, 0, -5, 0 },
                { 0, 0, 1, 0, 0, 0, -6 },
                { 0, 1, 1, -1, -1, 0, 0 },
                { 0, 0, 0, -1, 0, 0, 0 },
                { 0, 0, 0, 0, -1, 0, 0 },
                { 0, 0, 0, 0, 0, 1, 1},

            };// матрица коэффициентов
            int[] sin = { 1, 1, 3, 3, 3, 1 };// вектор знаков 1 это <=, 3 это =
            double[] b = { 0, 0, 0, -2, -3, 1 };// вектор ограничений правой части
            double[] min = { 0, 0, 0, 0, 0, 0 };// ограничения по минимальному значению
            double[] max = { 5, 6, 2, 3, 1, 1};// ограничения по максимальному значению

            optimate(f, m, sin, b, min, max);// вызываем функцию оптимизации
            System.out.println(getObjective());// выводим значение целевой функции
            System.out.println(Arrays.toString(getVariables()));// выводим значения переменных
        } catch (LpSolveException e) {
            e.printStackTrace();
        }
    }
}