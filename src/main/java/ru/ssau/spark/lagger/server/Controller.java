package ru.ssau.spark.lagger.server; /**
 * Created by Dmitry on 04.11.2017.
 */

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.spark.lagger.entity.CalculationConfig;
import ru.ssau.spark.lagger.logic.SparkExecutor;
import ru.ssau.spark.lagger.logic.TimeParameters;

import java.util.HashMap;
import java.util.Map;


@RestController
public class Controller {

    @Value("${spark.master.parallel}")
    private String master;

    @Value("${spark.driver.maxResultSize}")
    private String maxSize;

    @RequestMapping(value="/api/calc",method = RequestMethod.POST)
    public Object doCalculations(CalculationConfig config) {
        JavaSparkContext sc=null;
        try {
            TimeParameters timeParameters = new TimeParameters();
            TimeParameters timeParameters2 = new TimeParameters();
            SparkConf conf = new SparkConf().setAppName("appname").setMaster(master).set("spark.driver.maxResultSize", maxSize);

            sc = new JavaSparkContext(conf);
            SparkExecutor executor = new SparkExecutor(config, sc);
            executor.calculateParallel(timeParameters);
            sc.stop();

            conf.setMaster("local[1]").set("spark.default.parallelism","1").set("spark.executor.cores","1").set("spark.driver.cores","1");
            sc = new JavaSparkContext( conf);
            executor=new SparkExecutor(config,sc);
            System.gc();
            executor.calculateParallel(timeParameters2);
            sc.stop();

            Map<String,Object> result=new HashMap<>();
            result.put("prl",timeParameters);
            result.put("seq",timeParameters2);
            System.gc();
            return result;
        }catch(Exception e){
            e.printStackTrace();
            if(sc!=null) sc.stop();
        }
        return "internal error";
    }


}