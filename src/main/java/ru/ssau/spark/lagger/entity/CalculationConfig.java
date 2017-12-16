package ru.ssau.spark.lagger.entity;

import ru.ssau.spark.lagger.logic.CalculationUtils;

import java.io.Serializable;

/**
 * Created by Dmitry on 28.11.2017.
 */
public class CalculationConfig implements Serializable {

    private String method;
    private double h = CalculationUtils.DEFAULT_H;
    private double delta = CalculationUtils.DELTA_2E_NEG1;
    private double gamma;
    private double alfa;
    private int k;
    private int n;

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
