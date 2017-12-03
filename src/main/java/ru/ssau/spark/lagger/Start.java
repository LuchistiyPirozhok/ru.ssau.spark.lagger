package ru.ssau.spark.lagger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ssau.spark.lagger.math.MathUtils;
import ru.ssau.spark.lagger.server.Controller;

/**
 * Created by Dmitry on 28.11.2017.
 */
@SpringBootApplication
public class Start {
    public static void main(String[] args) throws Exception {
       // System.out.println(MathUtils.factorial(10));
        SpringApplication.run(Start.class, args);
    }
}
