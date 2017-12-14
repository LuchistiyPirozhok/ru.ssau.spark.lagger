import org.junit.Test;
import ru.ssau.spark.lagger.math.LagerrsFunction;

import static java.lang.Math.*;
import static junit.framework.TestCase.assertEquals;


public class FunctionTest {

    private double a = 2;
    private double t = 1;
    private double g = 4;
    private int k = 0;

    @Test
    public void zeroPowTest() {
        k = 0;

        float expected = (float) exp(-g * t / 2);
        assertEquals(expected, (float) LagerrsFunction.get(a, t, g, k));
    }

    @Test
    public void firstPowTest() {
        k = 1;

        float expected = (float) (exp(-g * t / 2) * (a + 1 - g * t));
        assertEquals(expected, (float) LagerrsFunction.get(a, t, g, k));
    }

    @Test
    public void secondPowTest() {
        k = 2;

        double first = getFirstArg(2);
        double second = pow(g, 2) * pow(t, 2) - 2 * (a + 2) * g * t + (a + 1) * (a + 2);
        float expected = (float) (first * second);
        assertEquals(expected, (float) LagerrsFunction.get(a, t, g, k));
    }

    @Test
    public void thirdPowTest() {
        k = 3;

        double first = getFirstArg(6);
        double second = (a + 1) * (a + 2) * (a + 3) - 3 * (a + 2) * (a + 3) * g * t + 3 * (a + 3) *
                pow(g, 2) * pow(t, 2) - pow(g, 3) * pow(t, 3);
        float expected = (float) (first * second);
        assertEquals(expected, (float) LagerrsFunction.get(a, t, g, k));
    }

    @Test
    public void firthPowTest() {
        k = 4;

        double first = getFirstArg(24);
        double second = pow(g, 4) * pow(t, 4) - 4 * (a + 4) * pow(g, 3) * pow(t, 3) + 6 * (a + 3)
                * (a + 4) * pow(g, 2) * pow(t, 2) - 4 * (a + 2) * (a + 3) * (a + 4) * g * t + (a + 1) * (a + 2)
                * (a + 3) * (a + 4);
        float expected = (float) (first * second);
        assertEquals(expected, (float) LagerrsFunction.get(a, t, g, k));
    }

    @Test
    public void fifthPowTest() {
        k = 5;

        double first = getFirstArg(120);
        double second = (a + 1) * (a + 2) * (a + 3) * (a + 4) * (a + 5) - 5 * (a + 2) * (a + 3) * (a + 4) * (a + 5)
                * g * t + 10 * (a + 3) * (a + 4) * (a + 5) * pow(g, 2) * pow(t, 2)
                - 10 * (a + 4) * (a + 5) * pow(g, 3) * pow(t, 3) + 5 * (a + 5)
                * pow(g, 4) * pow(t, 4) - pow(g, 5) * pow(t, 5);
        float expected = (float) (first * second);
        assertEquals(expected, (float) LagerrsFunction.get(a, t, g, k));
    }

    private double getFirstArg(double value) {
        return 1.0 / value * exp(-g * t / 2);
    }
}
