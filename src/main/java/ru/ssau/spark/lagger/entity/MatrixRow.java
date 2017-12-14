package ru.ssau.spark.lagger.entity;

import org.apache.spark.mllib.linalg.distributed.MatrixEntry;

import java.util.List;

/**
 * Created by Dmitry on 09.12.2017.
 */
public class MatrixRow {
    List<MatrixEntry> values;
    double deltaTao;
    double tkMax;

    public MatrixRow(List<MatrixEntry> values, double deltaTao, double tkMax) {
        this.values = values;
        this.deltaTao = deltaTao;
        this.tkMax = tkMax;
    }

    public List<MatrixEntry> getValues() {
        return values;
    }

    public double getDeltaTao() {
        return deltaTao;
    }

    public double getTkMax() {
        return tkMax;
    }
}
