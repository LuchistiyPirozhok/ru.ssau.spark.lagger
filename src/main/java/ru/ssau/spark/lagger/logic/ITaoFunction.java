package ru.ssau.spark.lagger.logic;

import java.io.Serializable;

/**
 * Created by Dmitry on 09.12.2017.
 */
public interface ITaoFunction  extends Serializable {
    double getValue(double tao);
    int getK();
}
