package ru.ssau.spark.lagger;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import ru.ssau.spark.lagger.entity.CalculationConfig;
import ru.ssau.spark.lagger.logic.SparkExecutor;
import ru.ssau.spark.lagger.logic.TimeParameters;

/**
 * Created by Dmitry on 28.11.2017.
 */
//@SpringBootApplication
public class Start {
    public static void main(String[] args) throws Exception {
       // System.out.println(MathUtils.factorial(10));
      // SpringApplication.run(Start.class, args);
      /*  System.out.println("tao\tfunction\tderivation\tintegral");
        for(double tao = 0; tao<100; tao+=1){
           System.out.print((tao+"\t").replace('.',','));
           System.out.print((LaggersFunction.get(2,tao,1,1)+"\t").replace('.',','));
           System.out.print((LaggersDerivative.get(1,2,tao,1,1)+"\t").replace('.',','));
           System.out.println((LaggersIntegral.get(1,2,tao,1)+"\t").replace('.',','));
        }*/
        SparkConf conf = new SparkConf().setAppName("appname").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        CalculationConfig config=  new CalculationConfig();
        config.setAlfa(0.1);
        config.setGamma(0.1);
        config.setK(2);
        config.setN(1);
        config.setMethod(SparkExecutor.METHOD_LAGGER);
        SparkExecutor executor = new SparkExecutor(config,sc);
        TimeParameters timeParameters = new TimeParameters();
        executor.calculateParallel(timeParameters);
        System.out.println(timeParameters);
      //  System.out.println(matrixRow.getValues().length);

    }
}
