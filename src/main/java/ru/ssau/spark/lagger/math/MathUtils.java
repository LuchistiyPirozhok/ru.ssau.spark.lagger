package ru.ssau.spark.lagger.math;

import static java.lang.Math.*;

/**
 * Created by Дима on 04.11.2017.
 */
public class MathUtils {
    public static double logGamma(double x) {
        double tmp = (x - 0.5) * log(x + 4.5) - (x + 4.5);
        double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
        return tmp + log(ser * sqrt(2 * PI));
    }
    public static double gamma(double x) { return exp(logGamma(x)); }

    public static double factorial(int p) {
        return gamma(p+1);
    }

    public static double C(double n, double k) {
        return factorial((int) n) / (factorial((int) k) * (factorial((int) (n - k))));
    }

    public static double C(double k, double s, double a) {
        return factorial( (int) (k + a))/(factorial( (int) (k - s))*(factorial( (int) (s + a))));
    }

}
