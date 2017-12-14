package ru.ssau.spark.lagger.logic;

import org.apache.spark.mllib.linalg.distributed.MatrixEntry;
import ru.ssau.spark.lagger.entity.MatrixRow;

import java.util.List;

import java.util.ArrayList;

/**
 * Created by Dmitry on 09.12.2017.
 */
public class CalculationUtils {
    public static final double DEFAULT_H=0.0001;
    public static final double DELTA_5E_NEG2 = 0.05;
    public static final double DELTA_2E_NEG2 = 0.02;
    public static final double DELTA_1E_NEG1 = 0.1;
    public static final double DELTA_2E_NEG1 = 0.2;

    public static double derivateNumeric(ITaoFunction function,double tao, double h){
        return (function.getValue(tao+h)-function.getValue(tao))/h;
    }
    public static double derivateNumeric(ITaoFunction function,double tao){
        return derivateNumeric(function,tao,DEFAULT_H);
    }

    public static double derivateNumeric2(ITaoFunction function,double tao,double h){
        return (function.getValue(tao+h)-2*function.getValue(tao)+function.getValue(tao-h))/Math.pow(h,2);
    }
    public static double derivateNumeric2(ITaoFunction function,double tao){
        return derivateNumeric2(function,tao,DEFAULT_H);
    }

    public static double getDeltaTao(ITaoFunction function, double delta, double h ){
        double secondDerivation = derivateNumeric2(function,0,h);
        return Math.sqrt(8*delta/Math.abs(secondDerivation));
    }

    public static double getDeltaTao(ITaoFunction function, double delta){
        return  getDeltaTao(function,delta,DEFAULT_H);
    }

    public static MatrixRow getMatrixRow(ITaoFunction function, double delta, double h){

        List<MatrixEntry> matrixEntries=new ArrayList<>();

        double tao = 0;
        double deltaTao = getDeltaTao(function,delta,h);
        boolean inDeltaCorridor = false;
        Double prevDerivationSignum=null;
        int i = 0;
        while(!inDeltaCorridor){
            double value = function.getValue(tao);
            matrixEntries.add(new MatrixEntry(i,function.getK(),value));
            i++;
            //if we enter the corridor
            if(Math.abs(value)<delta){
                //check if first derivation equals 0 or has different sign with previous step
                double derivation = derivateNumeric(function,tao,h);
                double derivationSignum = Math.signum(derivation);
                if(derivationSignum==0 || (prevDerivationSignum!=null && prevDerivationSignum!=derivationSignum)){
                    inDeltaCorridor=true;
                }
                prevDerivationSignum=derivationSignum;
            }else if(prevDerivationSignum!=null){
                //if we leave corridor clear threshold
                prevDerivationSignum=null;
            }
            tao+=deltaTao;
        }

        return new MatrixRow(matrixEntries,deltaTao,matrixEntries.size()*deltaTao);
    }


}
