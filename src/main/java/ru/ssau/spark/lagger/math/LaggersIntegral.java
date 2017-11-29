package ru.ssau.spark.lagger.math;
import static java.lang.Math.*;
import static ru.ssau.spark.lagger.math.MathUtils.C;
import static ru.ssau.spark.lagger.math.MathUtils.factorial;

/**
 * Created by Дима on 04.11.2017.
 */
public class LaggersIntegral {

    public static double get(int k, double a, double t, double g) {
        double result = -2.0/g * exp(-g*t/2.0);
        double firstSum=0;
        for (int s=0; s <= k; s++) {

            double secondSum = 0;
            for (int j=0; j <= s+1; j++) {
                secondSum += pow(2.0/g, j) * (pow(t, s+1-j)/factorial(s+1-j));
            }
            firstSum+=C(k+a, k-s)*pow(-g, s) * (s+1) * secondSum;
        }
        return result*firstSum;
    }
}
