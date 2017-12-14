package ru.ssau.spark.lagger.logic;

import ru.ssau.spark.lagger.math.LagerrsFunction;

/**
 * Created by Dmitry on 14.12.2017.
 */
public class LagerrTaoFanction implements ITaoFunction {
    private double a;
    private double g;
    private int k;

    public LagerrTaoFanction(double a, double g, int k) {
        this.a = a;
        this.g = g;
        this.k = k;
    }

    @Override
    public double getValue(double tao) {
        return LagerrsFunction.get(a,tao,g,k);
    }

    @Override
    public int getK() {
        return k;
    }
}
