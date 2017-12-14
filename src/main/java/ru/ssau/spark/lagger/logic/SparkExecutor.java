package ru.ssau.spark.lagger.logic;


import org.apache.commons.lang.ArrayUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.*;
import org.apache.spark.mllib.linalg.distributed.*;
import org.apache.spark.rdd.RDD;
import ru.ssau.spark.lagger.entity.CalculationConfig;
import ru.ssau.spark.lagger.entity.SingleTask;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by Dmitry on 28.11.2017.
 */
public class SparkExecutor {

    public static final String METHOD_LAGGER="lagger";
    public static final String METHOD_LAGGER_DERIVATIVE="lagger_der";
    public static final String METHOD_LAGGER_INTEGRAL="lagger_int";


    private CalculationConfig config;
    private JavaSparkContext context;
    private List<SingleTask> taskList;

    private long start;


    public SparkExecutor(CalculationConfig config, JavaSparkContext context){
        this.config=config;
        this.context=context;

    }
    public DenseMatrix calculateSequential(TimeParameters timeParameters){
        //RDD<MatrixEntry> entries = new RDD




        return null;
    }

    public BlockMatrix calculateParallel(TimeParameters timeParameters){
        System.out.println("******************************* BUILDING FUNCTIONS *******************************");
        List<ITaoFunction> functions = new ArrayList<>();
        switch(config.getMethod()){
            case METHOD_LAGGER:
                for(int i = 0; i<config.getK();i++){
                    functions.add(new LagerrTaoFanction(config.getAlfa(),config.getGamma(),i));
                }
                break;
            case METHOD_LAGGER_DERIVATIVE:
                for(int i = 0; i<config.getK();i++){
                    functions.add(new LagerrDerivativeTaoFunction(i,config.getAlfa(),config.getGamma(),config.getN()));
                }
                break;
            case METHOD_LAGGER_INTEGRAL:
                for(int i = 0; i<config.getK();i++){
                    functions.add(new LagerrIntegralTaoFunction(i,config.getAlfa(),config.getGamma()));
                }
                break;
        }


        JavaRDD<ITaoFunction> functionsRdd = context.parallelize(functions);
        System.out.println("******************************* COUNTING L *******************************");
        start();
        JavaRDD<List<MatrixEntry>> map = functionsRdd.map(function -> CalculationUtils.getMatrixRow(function, CalculationUtils.DELTA_1E_NEG1, CalculationUtils.DEFAULT_H).getValues());
        JavaRDD<MatrixEntry> flatten=map.flatMap(y->y.iterator());
        CoordinateMatrix matrix = new CoordinateMatrix(flatten.rdd());
        BlockMatrix matL = matrix.toBlockMatrix().cache();
        matL.validate();
        timeParameters.setTimeLParallel(end());
        System.out.println("******************************* COUNTING L*L^1 *******************************");
        start();
        BlockMatrix matLTransp = matL.transpose();
        BlockMatrix multiply = matL.multiply(matLTransp);
        timeParameters.setTimeLonLtParallel(end());
        matrix=null;
        matL=null;
        matLTransp=null;
        map=null;
        flatten=null;
        start();
        System.out.println("******************************* SVD *******************************");
        IndexedRowMatrix indexedRowMatrix = multiply.toIndexedRowMatrix();
        long cols = indexedRowMatrix.numCols();
        SingularValueDecomposition<IndexedRowMatrix, Matrix> svd = indexedRowMatrix.computeSVD((int) cols, true, indexedRowMatrix.computeSVD$default$3());
        indexedRowMatrix=null;
        System.out.println("******************************* DIAG S *******************************");
        double[] doubles = svd.s().toArray();
        List<MatrixEntry> invSEntries = new ArrayList<>();
        for(int i = 0 ; i<doubles.length;i++){
            //doubles[i]=1/doubles[i];
            invSEntries.add(new MatrixEntry(i,i,1/doubles[i]));
        }
        CoordinateMatrix invSCoord = new CoordinateMatrix(context.parallelize(invSEntries).rdd());

        invSEntries=null;
        BlockMatrix invS = invSCoord.toBlockMatrix();
        BlockMatrix U = svd.U().toBlockMatrix();
        System.out.println("******************************* V TO BLOCK MATRIX *******************************");
        Matrix vDense = svd.V();
        double[] vDenseArray = vDense.toArray();
        List<MatrixEntry> vEntries=new ArrayList<>();
        for(int i = 0; i < vDense.numRows(); i ++){
            for(int j = 0 ; j < vDense.numCols();j++){
                vEntries.add(new MatrixEntry(i,j,vDenseArray[i*vDense.numRows()+j]));
            }
        }
        BlockMatrix V = new CoordinateMatrix(context.parallelize(vEntries).rdd()).toBlockMatrix();
        System.out.println("******************************* V*S*U *******************************");
        timeParameters.setTimeLonLtInvertedParallel(end());
        return V.multiply(invS).multiply(U);
    }

    private void start(){
        start = System.currentTimeMillis();
    }
    private long end(){
        return System.currentTimeMillis()-start;
    }


}
