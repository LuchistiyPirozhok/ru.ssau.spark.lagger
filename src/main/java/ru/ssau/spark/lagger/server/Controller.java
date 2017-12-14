package ru.ssau.spark.lagger.server; /**
 * Created by Dmitry on 04.11.2017.
 */
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ssau.spark.lagger.entity.CalculationConfig;


@RestController
public class Controller {

    @Autowired
    private JavaSparkContext context;

    @RequestMapping(value="/api/calc",method = RequestMethod.POST)
    public Object doCalculations(CalculationConfig config) {
        return "";
    }


}