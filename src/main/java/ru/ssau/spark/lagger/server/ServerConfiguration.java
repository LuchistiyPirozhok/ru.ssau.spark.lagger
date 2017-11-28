package ru.ssau.spark.lagger.server;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dmitry on 28.11.2017.
 */
@Configuration
public class ServerConfiguration {

    @Bean
    public JavaSparkContext getSparcContext(){
        SparkConf conf = new SparkConf().setAppName("appname").setMaster("local[2]");
        JavaSparkContext sc = new JavaSparkContext(conf);
        return sc;
    }

}
