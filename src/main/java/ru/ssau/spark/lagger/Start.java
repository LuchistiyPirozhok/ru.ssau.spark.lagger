package ru.ssau.spark.lagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.spark.lagger.logic.CalculationUtils;
import ru.ssau.spark.lagger.math.LagerrsFunction;
import ru.ssau.spark.lagger.math.LagerrsIntegral;
import ru.ssau.spark.lagger.math.LaggersDerivative;
import ru.ssau.spark.lagger.math.MathUtils;


/**
 * Created by Dmitry on 28.11.2017.
 */
@SpringBootApplication
public class Start {
    public static void main(String[] args) throws Exception {
        new LaggersDerivative();
        new LagerrsFunction();
        new LagerrsIntegral();
        new MathUtils();
        new CalculationUtils();

        SpringApplication.run(Start.class, args);

    }
}
