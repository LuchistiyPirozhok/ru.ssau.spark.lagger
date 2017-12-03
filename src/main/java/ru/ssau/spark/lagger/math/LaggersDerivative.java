package ru.ssau.spark.lagger.math;

import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static ru.ssau.spark.lagger.math.MathUtils.C;
import static ru.ssau.spark.lagger.math.MathUtils.factorial;

/**
 * @author Alexey Mazaev
 */
public class LaggersDerivative {

    public static double get(int k, double alfa, double t, double gamma, int n) {
        double result = pow(-gamma, n) * exp((-gamma * t) / 2);
        double summa = 0;

        for (int j = 0; j <= n; j++) {
            summa += C(j, n) * pow(2, -j) * calculateSum(k, alfa, gamma, t, j, n);
        }

        return result * summa;
    }

    public static double getSecondPartDerivative(double alfa, double gamma, double t, int n) {
        double leftPart = pow(-gamma, n) * exp((-gamma * t) / 2);
        double rightPart = 0;

        for (int j = 0; j <= n; j++) {
            double left = C(j, n) * pow(2, -j);

            double firstSum = -n + j >= 0 ?
                    (alfa + 1) * (alfa + 2) * pow(-gamma * t, n + j) / (2 * factorial(-n + j)) : 0;
            double secondSum = 1 - n + j >= 0 ?
                    (alfa + 2) * pow(-gamma * t, 1 - n + j) / factorial(2 - n + j) : 0;

            double thirdSum = 2 - n + j >= 0 ?
                    pow(-gamma * t, 2 - n + j) / factorial(2 - n + j) : 0;

            rightPart += left * (firstSum + secondSum + thirdSum);
        }


        return leftPart * rightPart;
    }

    private static double calculateSum(int k, double alfa, double gamma, double t, int j, int n) {
        double result = 0;

        for (int s = 0; s <= k; s++) {
            result += C(k, s, alfa) * calculateBiFunction(s, n, j, gamma, t);
        }
        return result;
    }

    private static double calculateBiFunction(int s, int n, int j, double gamma, double t) {
        return s - n + j >= 0 ? pow(-gamma * t, s - n + j) / factorial(s - n + j) : 0;
    }
}