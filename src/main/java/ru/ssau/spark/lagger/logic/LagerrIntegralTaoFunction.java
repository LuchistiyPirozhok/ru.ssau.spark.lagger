package ru.ssau.spark.lagger.logic;

import ru.ssau.spark.lagger.math.LagerrsIntegral;

/**
 * Created by Dmitry on 14.12.2017.
 */
public class LagerrIntegralTaoFunction implements ITaoFunction {
    private int k;
    private double a;
    private double g;

    public LagerrIntegralTaoFunction(int k, double a, double g) {
        this.k = k;
        this.a = a;
        this.g = g;
    }

    @Override
    public double getValue(double tao) {
        return LagerrsIntegral.get(k,a,tao,g);
    }

    @Override
    public int getK() {
        return k;
    }
}
