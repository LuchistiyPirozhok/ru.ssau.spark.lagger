package ru.ssau.spark.lagger.math;
import static java.lang.Math.*;
import static ru.ssau.spark.lagger.math.MathUtils.C;

/**
 * Created by Дима on 04.11.2017.
 */
public class LaggersIntegral {

    public static double get(double k, double a, double t, double g) {
        double result = -2/g * exp(-g*t/2);
        double firstSum=0;
        for (int s=0; s <= k; s++) {

            double secondSum = 0;
            for (int j=0; j <= s+1; j++) {
                secondSum += pow(2.0/g, j) * (pow(t, s+1-j)/(s+1-j));
            }
            firstSum+=C(k-s, k+a)*pow(-g, s) * (s+1) * secondSum;
        }
        return result*firstSum;
    }
}
