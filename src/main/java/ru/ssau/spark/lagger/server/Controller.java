package ru.ssau.spark.lagger.server; /**
 * Created by Dmitry on 04.11.2017.
 */
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ssau.spark.lagger.entity.CalculationConfig;
import ru.ssau.spark.lagger.entity.SingleTask;
import ru.ssau.spark.lagger.logic.LaggerStrategy;
import ru.ssau.spark.lagger.logic.SparkExecutor;

import java.util.HashMap;
import java.util.List;

@RestController
public class Controller {
    private static final String METHOD_LAGGER="lagger";
    @Autowired
    private JavaSparkContext context;

    @RequestMapping(value="/api/calc",method = RequestMethod.POST)
    public Object doCalculations(CalculationConfig config) {
        switch (config.getMethod()){
            case METHOD_LAGGER:
                SparkExecutor executor = new SparkExecutor(config,new LaggerStrategy(),context);
                HashMap<String,Object> answer=new HashMap<>();
                long start = System.currentTimeMillis();
                List<SingleTask> result = executor.calculate();
                long end = System.currentTimeMillis();
                answer.put("time",end-start);
                answer.put("results",result);
                return answer;
            default:
                return "unsupported method";
        }
    }


}