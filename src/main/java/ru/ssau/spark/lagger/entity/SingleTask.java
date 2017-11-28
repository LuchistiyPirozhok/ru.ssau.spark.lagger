package ru.ssau.spark.lagger.entity;

import java.io.Serializable;

/**
 * Created by Dmitry on 28.11.2017.
 */
public class SingleTask implements Serializable{
    private int k;
    private double t;
    private double g;
    private double a;

    private double result;

    public SingleTask(int k, double t, double g, double a) {
        this.k = k;
        this.t = t;
        this.g = g;
        this.a = a;
    }

    public SingleTask setResult(double result) {
        this.result = result;
        return this;
    }

    public int getK() {
        return k;
    }

    public double getT() {
        return t;
    }

    public double getG() {
        return g;
    }

    public double getA() {
        return a;
    }

    public double getResult() {
        return result;
    }
}
