package ru.ssau.spark.lagger.math;

import static java.lang.Math.*;
import static ru.ssau.spark.lagger.math.MathUtils.C;

public class LaggersFunction {

    public static double get(double a, double t, double g, int k) {
        double result = 0;
        for (int s = 0; s <= k; s++) {
            result += C(k, s, a) * pow((-g * t), s) / MathUtils.factorial(s) * exp(-g * t / 2);
        }
        return result;
    }
}
