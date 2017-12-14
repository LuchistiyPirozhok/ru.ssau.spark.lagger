import org.junit.Test;
import ru.ssau.spark.lagger.math.LagerrsIntegral;

import static junit.framework.TestCase.assertEquals;
import static java.lang.Math.*;

/**
 * Created by Дима on 04.11.2017.
 */

public class IntegralTest {

    private static double t = 1;

    public double Fith(double a, double t, double g) {
        return 1.0/(60*pow(g, 2)) * exp(-g*t/2.0)*(pow(g,6)*pow(t,6)-(5*a +13)*pow(g,5)*pow(t,5)+
                10*(pow(a,2)+4*a+7)*pow(g,4)*pow(t,4)-10*(pow(a,3)+4*pow(a,2)+15*a+4)*pow(g,3)*pow(t,3)+5*(pow(a,4)+2*pow(a,3)+
                23*pow(a,2)-26*a+72)*pow(g,2)*pow(t,2)-(pow(a,5)-5*pow(a,4)+45*pow(a,3)-235*pow(a,2)+794*a
                -1320)*g*t-2*(pow(a,5)-5*pow(a,4)+45*pow(a,3)-235*pow(a,2)+794*a-1320));
    }

    public double Fourth(double a, double t, double g) {
        return -1.0/(12*pow(g,2)) * exp(-g*t/2.0)*(pow(g,5)*pow(t,5)-2*(2*a+3)*pow(g,4)*pow(t,4)+
                2*(3*pow(a,2)+5*a+12)*pow(g,3)*pow(t,3)-4*(pow(a,3)+11*a-12)*pow(g,2)*pow(t,2)+(pow(a,4)+6*pow(a,3)+
                35*pow(a,2)-126*a+216)*g*t+2*(pow(a,4)-6*pow(a,3)+35*pow(a,2)-126*a+216));
    }

    public double Third(double a, double t, double g) {
        return 1.0/(3*pow(g,2))*exp(-g*t/2.0)*(pow(g,4)*pow(t,4)-(3*a+1)*pow(g,3)*pow(t,3)+
                3*(pow(a,2) - a + 4)*pow(g,2)*pow(t,2)-(pow(a,3)-6*pow(a,2)+23*a-42)*g*t - 2*(pow(a,3)+6*pow(a,2) -
                23*a+42));
    }

    public double Second(double a, double t, double g) {
        return -1.0/pow(g,2) * exp(-g*t/2.0)*(pow(g,3)*pow(t,3)-2*(a-1)*pow(g,2)*pow(t,2)+
                (pow(a,2)-5*a+10)*g*t + 2*pow(a,2)-10*a+20);
    }

    public double First(double a, double t, double g) {
        return 2/pow(g,2)*exp(-g*t/2.0)*(pow(g,2)*pow(t,2)-(a-3)*g*t-2*a+6);
    }

    public double Zero(double a, double t, double g) {
        return -2/pow(g,2)*exp(-g*t/2.0)*(g*t+2);
    }

    @Test
    public void testZero() {
        double testValue = Math.floor(LagerrsIntegral.get(0,1,t,2) * 1000000) / 1000000;
        double expectedValue = Math.floor(Zero(1,t,2)*1000000)/1000000;

        assertEquals("zero test", testValue, expectedValue);
    }

    @Test
    public void testFirst() {
        double testValue = Math.floor(LagerrsIntegral.get(1,1,t,2) * 1000000) / 1000000;
        double expectedValue = Math.floor(First(1,t,2)*1000000)/1000000;

        assertEquals("first test", testValue, expectedValue);
    }

    @Test
    public void testSecond() {
        float testValue = (float) LagerrsIntegral.get(2,1,t,2);
        float expectedValue = (float)Second(1,t,2);

        assertEquals("second test", testValue, expectedValue);
    }

    @Test
    public void testThird() {
        float testValue = (float) LagerrsIntegral.get(3,1,25,2);
        float expectedValue = (float)Third(1,25,2);

        assertEquals("third test", testValue, expectedValue);
    }

    @Test
    public void testFourth() {
        double testValue = Math.floor(LagerrsIntegral.get(4,1,11,2) * 1000) / 1000;
        double expectedValue = Math.floor(Fourth(1,11,2)*1000)/1000;

        assertEquals("fourth test", testValue, expectedValue);
    }

    @Test
    public void testFith() {
        double testValue = Math.floor(LagerrsIntegral.get(5,1,t,2) * 1000000) / 1000000;
        double expectedValue = Math.floor(Fith(1,t,2)*1000000)/1000000;

        assertEquals("fith test", testValue, expectedValue);
    }
}
