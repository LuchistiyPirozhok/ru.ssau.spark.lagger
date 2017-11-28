package ru.ssau.spark.lagger.logic;

import ru.ssau.spark.lagger.entity.SingleTask;
import ru.ssau.spark.lagger.math.LaggersFunction;

/**
 * Created by Dmitry on 28.11.2017.
 */
public class LaggerStrategy implements CalculationStrategy {

    @Override
    public SingleTask execute(SingleTask task) {
        double result = LaggersFunction.get(task.getA(), task.getT(), task.getG(), task.getK());
        return task.setResult(result);
    }
}
