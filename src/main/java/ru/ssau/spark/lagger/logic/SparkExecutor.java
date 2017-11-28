package ru.ssau.spark.lagger.logic;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import ru.ssau.spark.lagger.entity.CalculationConfig;
import ru.ssau.spark.lagger.entity.SingleTask;
import java.util.List;

import java.util.ArrayList;

/**
 * Created by Dmitry on 28.11.2017.
 */
public class SparkExecutor {

    private CalculationConfig config;
    private CalculationStrategy strategy;
    private JavaSparkContext context;
    private List<SingleTask> taskList;

    public SparkExecutor(CalculationConfig config, CalculationStrategy strategy, JavaSparkContext context){
        this.config=config;
        this.context=context;
        this.strategy=strategy;
    }

    private void initTasks(){
        taskList=new ArrayList<>();

        double aTo = config.getaTo();
        double aFrom = config.getaFrom();
        double deltaA = (aTo - aFrom)/(double)(config.getaCount()-1);
        double gTo = config.getgTo();
        double gFrom = config.getgFrom();
        double deltaG = (gTo - gFrom)/(double)(config.getgCount()-1);
        int kFrom = config.getkFrom();
        int kTo = config.getkTo();
        int t =config.getT();
        for(double a = aFrom; a<= aTo; a+=deltaA){
            for(double g = gFrom; g<= gTo; g+=deltaG){
                for(int k = kFrom; k<= kTo; k++){
                    taskList.add(new SingleTask(k,t,g,a));
                }
            }
        }
    }

    public List<SingleTask> calculate(){
        initTasks();
        JavaRDD<SingleTask> parallelize = context.parallelize(taskList);
        JavaRDD<SingleTask> map = parallelize.map(strategy::execute);
        return map.collect();
    }


}
