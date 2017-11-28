package ru.ssau.spark.lagger.logic;

import ru.ssau.spark.lagger.entity.SingleTask;

import java.io.Serializable;

/**
 * Created by Dmitry on 28.11.2017.
 */
public interface CalculationStrategy extends Serializable {
    public SingleTask execute(SingleTask task);
}
