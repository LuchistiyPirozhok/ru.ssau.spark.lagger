package ru.ssau.spark.lagger.entity;

/**
 * Created by Dmitry on 28.11.2017.
 */
public class CalculationConfig {

    private String method;

    private double aFrom;
    private double aTo;
    private int aCount;

    private int t;

    private double gFrom;
    private double gTo;
    private int gCount;

    private int kFrom;
    private int kTo;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public double getaFrom() {
        return aFrom;
    }

    public void setaFrom(double aFrom) {
        this.aFrom = aFrom;
    }

    public double getaTo() {
        return aTo;
    }

    public void setaTo(double aTo) {
        this.aTo = aTo;
    }

    public int getaCount() {
        return aCount;
    }

    public void setaCount(int aCount) {
        this.aCount = aCount;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public double getgFrom() {
        return gFrom;
    }

    public void setgFrom(double gFrom) {
        this.gFrom = gFrom;
    }

    public double getgTo() {
        return gTo;
    }

    public void setgTo(double gTo) {
        this.gTo = gTo;
    }

    public int getgCount() {
        return gCount;
    }

    public void setgCount(int gCount) {
        this.gCount = gCount;
    }

    public int getkFrom() {
        return kFrom;
    }

    public void setkFrom(int kFrom) {
        this.kFrom = kFrom;
    }

    public int getkTo() {
        return kTo;
    }

    public void setkTo(int kTo) {
        this.kTo = kTo;
    }
}
