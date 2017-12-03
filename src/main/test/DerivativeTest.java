import org.junit.Test;
import ru.ssau.spark.lagger.math.LaggersDerivative;

import java.util.ArrayList;

import static java.lang.Math.*;
import static junit.framework.TestCase.assertEquals;
import static ru.ssau.spark.lagger.math.MathUtils.C;
import static ru.ssau.spark.lagger.math.MathUtils.factorial;

public class DerivativeTest {

    private final int MAGIC_ALFA = 1;
    private final double MAGIC_T = 1;
    private final int MAGIC_GAMMA = 1;
    private final int MAGIC_N = MAGIC_GAMMA;


    private double calculateLeftPart() {
        return pow(-MAGIC_GAMMA, MAGIC_N) * exp((-MAGIC_GAMMA * MAGIC_T) / 2);
    }

    private double findN(int k, double gamma) {
        double deltaT = sqrt(8 * 0.05 / LaggersDerivative.getSecondPartDerivative(MAGIC_ALFA, MAGIC_GAMMA, 0, MAGIC_N)) +
                sqrt(8 * 0.02 / LaggersDerivative.getSecondPartDerivative(MAGIC_ALFA, MAGIC_GAMMA, 0, MAGIC_N)) +
                sqrt(8 * 0.1 / LaggersDerivative.getSecondPartDerivative(MAGIC_ALFA, MAGIC_GAMMA, 0, MAGIC_N)) +
                sqrt(8 * 0.2 / LaggersDerivative.getSecondPartDerivative(MAGIC_ALFA, MAGIC_GAMMA, 0, MAGIC_N));

        ArrayList<Double> dots = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            double y = LaggersDerivative.get(k, MAGIC_ALFA, MAGIC_GAMMA, deltaT, MAGIC_N);
            dots.add(y);
        }

        double tkMax = 0;

        for (Double dot : dots) {
            if (abs(dot) > gamma) {
                tkMax = dot;
            }
        }

        return tkMax / deltaT + 0.5;
    }


    @Test
    public void Zero() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        double a = findN(0, MAGIC_GAMMA);

        for (int j = 0; j < MAGIC_N; j++) {
            double leftPart = C(MAGIC_N, j) * pow(2, -j);

            rightPart += leftPart * (-MAGIC_N + j >= 0 ? pow(-MAGIC_GAMMA * j, -MAGIC_N + j) / factorial(-MAGIC_N + j) : 0);
        }

        expectedResult = floor(expectedResult * rightPart);

        double realResult = floor(LaggersDerivative.get(0, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N));

        assertEquals(expectedResult, realResult);
    }

    @Test
    public void First() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j < MAGIC_N; j++) {
            double leftPart = C(MAGIC_N, j) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 1) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / factorial(-MAGIC_N + j) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ?
                    pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / factorial(1 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm);
        }

        expectedResult = expectedResult * rightPart
        ;

        double realResult = LaggersDerivative.get(1, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N);

        assertEquals(expectedResult, realResult);
    }

    @Test
    public void Second() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j < MAGIC_N; j++) {
            double leftPart = C(MAGIC_N, j) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 1) * (MAGIC_ALFA + 2) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / (2 * factorial(-MAGIC_N + j)) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 2) * pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / factorial(1 - MAGIC_N + j) : 0;
            double thirdTerm = 2 - MAGIC_N + j >= 0 ?
                    pow(-MAGIC_GAMMA * MAGIC_T, 2 - MAGIC_N + j) / factorial(2 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm + thirdTerm);
        }

        expectedResult = expectedResult * rightPart;

        double realResult = LaggersDerivative.get(2, MAGIC_ALFA, 9, MAGIC_GAMMA, MAGIC_N);

        assertEquals(expectedResult, realResult);
    }

    @Test
    public void Third() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j < MAGIC_N; j++) {
            double leftPart = C(MAGIC_N, j) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ? factorial(MAGIC_ALFA + 3) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / (6 * factorial(MAGIC_ALFA) * factorial(-MAGIC_N + j)) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ? (MAGIC_ALFA + 2) * (MAGIC_ALFA + 3) * pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / (2 * factorial(1 - MAGIC_N + j)) : 0;
            double thirdTerm = 2 - MAGIC_N + j >= 0 ? (MAGIC_ALFA + 3) * pow(-MAGIC_GAMMA * MAGIC_T, 2 - MAGIC_N + j) / factorial(2 - MAGIC_N + j) : 0;
            double fourthTerm = 3 - MAGIC_N + j >= 0 ? pow(-MAGIC_GAMMA * MAGIC_T, 3 - MAGIC_N + j) / factorial(3 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm + thirdTerm + fourthTerm);
        }

        expectedResult = floor(expectedResult * rightPart);

        double realResult = floor(LaggersDerivative.get(3, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N));

        assertEquals(expectedResult, realResult);

    }


    @Test
    public void Fourth() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j < MAGIC_N; j++) {
            double leftPart = C(MAGIC_N, j) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ? factorial(MAGIC_ALFA + 4) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / (24 * factorial(MAGIC_ALFA) * factorial(-MAGIC_N + j)) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ? factorial(MAGIC_ALFA + 3) * pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / (6 * factorial(MAGIC_ALFA + 1) * factorial(1 - MAGIC_N + j)) : 0;
            double thirdTerm = 2 - MAGIC_N + j >= 0 ? (MAGIC_ALFA + 3) * (MAGIC_ALFA + 4) * pow(-MAGIC_GAMMA * MAGIC_T, 2 - MAGIC_N + j) / (2 * factorial(2 - MAGIC_N + j)) : 0;
            double fourthTerm = 3 - MAGIC_N + j >= 0 ? (MAGIC_ALFA + 4) * pow(-MAGIC_GAMMA * MAGIC_T, 3 - MAGIC_N + j) / factorial(3 - MAGIC_N + j) : 0;
            double fithTerm = 4 - MAGIC_N + j >= 0 ? pow(-MAGIC_GAMMA * MAGIC_T, 4 - MAGIC_N + j) / factorial(4 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm + thirdTerm + fourthTerm + fithTerm);
        }

        expectedResult = floor(expectedResult * rightPart);

        double realResult = floor(LaggersDerivative.get(4, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N));

        assertEquals(expectedResult, realResult);

    }

    @Test
    public void Fith() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j < MAGIC_N; j++) {
            double leftPart = C(MAGIC_N, j) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / (120 * factorial(MAGIC_ALFA) * factorial(-MAGIC_N + j)) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / (24 * factorial(MAGIC_ALFA + 1) * factorial(1 - MAGIC_N + j)) : 0;
            double thirdTerm = 2 - MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 2 - MAGIC_N + j) / (6 * factorial(MAGIC_ALFA + 2) + factorial(2 - MAGIC_N + j)) : 0;
            double fourthTerm = 3 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 4) * (MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 3 - MAGIC_N + j) / (2 * factorial(3 - MAGIC_N + j)) : 0;
            double fithTerm = 4 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 5 - MAGIC_N + j) / factorial(4 - MAGIC_N + j) : 0;
            double sixTerm = 5 - MAGIC_N + j >= 0 ?
                    pow(-MAGIC_GAMMA * MAGIC_T, 5 - MAGIC_N + j) / factorial(5 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm + thirdTerm + fourthTerm + fithTerm + sixTerm);
        }

        expectedResult = floor(expectedResult * rightPart);

        double realResult = floor(LaggersDerivative.get(5, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N));

        assertEquals(expectedResult, realResult);
    }

    class Dot {
        public double x;
        public double y;
    }

}