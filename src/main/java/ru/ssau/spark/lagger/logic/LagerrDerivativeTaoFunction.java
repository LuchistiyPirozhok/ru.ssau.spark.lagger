package ru.ssau.spark.lagger.logic;

import ru.ssau.spark.lagger.math.LagerrsDerivative;

/**
 * Created by Dmitry on 14.12.2017.
 */
public class LagerrDerivativeTaoFunction implements ITaoFunction {
    private int k;
    private double alfa;
    private double gamma;
    private int n;

    public LagerrDerivativeTaoFunction(int k, double alfa, double gamma, int n) {
        this.k = k;
        this.alfa = alfa;
        this.gamma = gamma;
        this.n = n;
    }

    @Override
    public double getValue(double tao) {
        return LagerrsDerivative.get(k,alfa,tao,gamma,n);
    }

    @Override
    public int getK() {
        return k;
    }
}
