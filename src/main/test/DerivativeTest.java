import org.junit.Test;
import ru.ssau.spark.lagger.math.LaggersDerivative;

import static java.lang.Math.*;
import static junit.framework.TestCase.assertEquals;
import static ru.ssau.spark.lagger.math.MathUtils.C;
import static ru.ssau.spark.lagger.math.MathUtils.factorial;

public class DerivativeTest {

    private final int MAGIC_ALFA = 1;
    private final double MAGIC_T = 1;
    private final int MAGIC_GAMMA = 2;
    private final int MAGIC_N = 2;


    private double calculateLeftPart() {
        return pow(-MAGIC_GAMMA, MAGIC_N) * exp((-MAGIC_GAMMA * MAGIC_T) / 2);
    }

    @Test
    public void Zero() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j <= MAGIC_N; j++) {
            double leftPart = C(j, MAGIC_N) * pow(2, -j);
            rightPart += leftPart * (-MAGIC_N + j >= 0 ? pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / factorial(-MAGIC_N + j) : 0);
        }

        expectedResult = floor(expectedResult * rightPart);

        double realResult = floor(LaggersDerivative.get(0, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N));

        assertEquals(expectedResult, realResult);
    }

    @Test
    public void First() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j <= MAGIC_N; j++) {
            double leftPart = C(j, MAGIC_N) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 1) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / factorial(-MAGIC_N + j) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ?
                    pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / factorial(1 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm);
        }

        expectedResult = floor(expectedResult * rightPart);

        double realResult = floor(LaggersDerivative.get(1, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N));

        assertEquals(expectedResult, realResult);
    }

    @Test
    public void Second() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j <= MAGIC_N; j++) {
            double leftPart = C(j, MAGIC_N) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 1) * (MAGIC_ALFA + 2) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / (2 * factorial(-MAGIC_N + j)) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 2) * pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / factorial(1 - MAGIC_N + j) : 0;
            double thirdTerm = 2 - MAGIC_N + j >= 0 ?
                    pow(-MAGIC_GAMMA * MAGIC_T, 2 - MAGIC_N + j) / factorial(2 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm + thirdTerm);
        }

        expectedResult = floor(expectedResult * rightPart);

        double realResult = floor(LaggersDerivative.get(2, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N));

        assertEquals(expectedResult, realResult);
    }

    @Test
    public void Third() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j <= MAGIC_N; j++) {
            double leftPart = C(j, MAGIC_N) * pow(2, -j);

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

        for (int j = 0; j <= MAGIC_N; j++) {
            double leftPart = C(j, MAGIC_N) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 4) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / (24 * factorial(MAGIC_ALFA) * factorial(-MAGIC_N + j)) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 3) * pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / (6 * factorial(MAGIC_ALFA + 1) * factorial(1 - MAGIC_N + j)) : 0;
            double thirdTerm = 2 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 3) * (MAGIC_ALFA + 4) * pow(-MAGIC_GAMMA * MAGIC_T, 2 - MAGIC_N + j) / (2 * factorial(2 - MAGIC_N + j)) : 0;
            double fourthTerm = 3 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 4) * pow(-MAGIC_GAMMA * MAGIC_T, 3 - MAGIC_N + j) / factorial(3 - MAGIC_N + j) : 0;
            double fithTerm = 4 - MAGIC_N + j >= 0 ?
                    pow(-MAGIC_GAMMA * MAGIC_T, 4 - MAGIC_N + j) / factorial(4 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm + thirdTerm + fourthTerm + fithTerm);
        }

        expectedResult = expectedResult * rightPart;

        double realResult = LaggersDerivative.get(4, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N);

        assertEquals(expectedResult, realResult);
    }

    @Test
    public void Fith() {
        double expectedResult = calculateLeftPart();
        double rightPart = 0;

        for (int j = 0; j <= MAGIC_N; j++) {
            double leftPart = C(MAGIC_N, j, 0) * pow(2, -j);

            double firstTerm = -MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, -MAGIC_N + j) / (120 * factorial(MAGIC_ALFA) * factorial(-MAGIC_N + j)) : 0;
            double secondTerm = 1 - MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 1 - MAGIC_N + j) / (24 * factorial(MAGIC_ALFA + 1) * factorial(1 - MAGIC_N + j)) : 0;
            double thirdTerm = 2 - MAGIC_N + j >= 0 ?
                    factorial(MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 2 - MAGIC_N + j) / (6 * factorial(MAGIC_ALFA + 2) + factorial(2 - MAGIC_N + j)) : 0;
            double fourthTerm = 3 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 4) * (MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 3 - MAGIC_N + j) / (2 * factorial(3 - MAGIC_N + j)) : 0;
            double fithTerm = 4 - MAGIC_N + j >= 0 ?
                    (MAGIC_ALFA + 5) * pow(-MAGIC_GAMMA * MAGIC_T, 4 - MAGIC_N + j) / factorial(4 - MAGIC_N + j) : 0;
            double sixTerm = 5 - MAGIC_N + j >= 0 ?
                    pow(-MAGIC_GAMMA * MAGIC_T, 5 - MAGIC_N + j) / factorial(5 - MAGIC_N + j) : 0;

            rightPart += leftPart * (firstTerm + secondTerm + thirdTerm + fourthTerm + fithTerm + sixTerm);
        }

        expectedResult = expectedResult * rightPart;

        double realResult = LaggersDerivative.get(5, MAGIC_ALFA, MAGIC_T, MAGIC_GAMMA, MAGIC_N);

        assertEquals(expectedResult, realResult);
    }
}