package ru.ssau.spark.lagger.math;

import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static ru.ssau.spark.lagger.math.MathUtils.C;
import static ru.ssau.spark.lagger.math.MathUtils.factorial;


/**
 * @author Alexey Mazaev
 */
public class LaggersDerivative {

    public static double get(double k, double alfa, double t, double gamma, double n) {

        double result = (pow(-gamma, n)) * exp((-gamma * t) / 2);

        double summa = 0;

        for (int j = 0; j < n; j++) {
            summa += C(n, j) * pow(2, -j) * calculateSum(k, alfa, gamma, t, j, n);
        }

        return result * summa;
    }


    private static double calculateSum(double k, double alfa, double gamma, double t, double j, double n) {
        double result = 0;

        for (int s = 0; s < k; s++) {
            result += C(k, s, alfa) * calculateBiFunction(s, n, j, gamma, t);
        }
        return result;
    }


    private static double calculateBiFunction(double s, double n, double j, double gamma, double t) {
        return s - n + j >= 0 ? pow(-gamma * t, s - n + j) / factorial((s - n + j)) : 0;
    }

}
