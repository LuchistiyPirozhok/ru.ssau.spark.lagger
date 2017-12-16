package ru.ssau.spark.lagger.logic;


import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.*;
import org.apache.spark.mllib.linalg.distributed.*;
import ru.ssau.spark.lagger.entity.CalculationConfig;
import ru.ssau.spark.lagger.entity.MatrixRow;
import ru.ssau.spark.lagger.entity.SingleTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Dmitry on 28.11.2017.
 */
public class SparkExecutor implements Serializable {

    public static final String METHOD_LAGGER="lagger";
    public static final String METHOD_LAGGER_DERIVATIVE="lagger_der";
    public static final String METHOD_LAGGER_INTEGRAL="lagger_int";


    private CalculationConfig config;
    private JavaSparkContext context;

    private long start;


    public SparkExecutor(CalculationConfig config, JavaSparkContext context){
        this.config=config;
        this.context=context;
    }

    public double[][] calculateSequential(TimeParameters timeParameters){
        start();
        List<MatrixRow> rows = new ArrayList<>();
        int maxLength=0;
        switch(config.getMethod()){
            case METHOD_LAGGER:
                for(int i = 0; i<=config.getK();i++){
                    MatrixRow row =CalculationUtils.getMatrixRow(
                            new LagerrTaoFanction(config.getAlfa(),config.getGamma(),i),
                            config.getDelta(), config.getH());
                    if(maxLength<row.getValues().size()) maxLength = row.getValues().size();
                    rows.add(row);
                }
                break;
            case METHOD_LAGGER_DERIVATIVE:
                for(int i = 0; i<=config.getK();i++){
                    MatrixRow row =CalculationUtils.getMatrixRow(
                            new LagerrDerivativeTaoFunction(i,config.getAlfa(),config.getGamma(),config.getN()),
                            config.getDelta(), config.getH());
                    if(maxLength<row.getValues().size()) maxLength = row.getValues().size();
                    rows.add(row);

                }
                break;
            case METHOD_LAGGER_INTEGRAL:
                for(int i = 0; i<=config.getK();i++){
                    MatrixRow row =CalculationUtils.getMatrixRow(
                            new LagerrIntegralTaoFunction(i,config.getAlfa(),config.getGamma()),
                            config.getDelta(), config.getH());
                    if(maxLength<row.getValues().size()) maxLength = row.getValues().size();
                    rows.add(row);
                }
                break;
        }
        double[][] l = new double[maxLength][rows.size()];
        for(MatrixRow row: rows){
            for(MatrixEntry entry: row.getValues()){
                l[(int) entry.i()][(int) entry.j()]=entry.value();
            }
        }
        timeParameters.setTimeL(end());
        start();
        double[][] lonLt=new double[maxLength][maxLength];
        for(int i = 0; i<maxLength;i++){
            for(int j = 0; j<maxLength;j++){
                double sum = 0;
                for(int k = 0 ; k<rows.size();k++){
                    sum+=l[i][k]*l[j][k];
                }
                lonLt[i][j]=(sum<1E-9 || Double.isNaN(sum)) ? 0 : sum;
            }
        }
        timeParameters.setTimeLonLt(end());
        start();
        double[][] invert = CalculationUtils.invert(lonLt);
        timeParameters.setTimeLonLtInverted(end());
        return invert;
    }

    public BlockMatrix calculateParallel(TimeParameters timeParameters){
        System.out.println("******************************* BUILDING FUNCTIONS *******************************");
        List<ITaoFunction> functions = new ArrayList<>();
        switch(config.getMethod()){
            case METHOD_LAGGER:
                for(int i = 0; i<=config.getK();i++){
                    functions.add(new LagerrTaoFanction(config.getAlfa(),config.getGamma(),i));
                }
                break;
            case METHOD_LAGGER_DERIVATIVE:
                for(int i = 0; i<=config.getK();i++){
                    functions.add(new LagerrDerivativeTaoFunction(i,config.getAlfa(),config.getGamma(),config.getN()));
                }
                break;
            case METHOD_LAGGER_INTEGRAL:
                for(int i = 0; i<=config.getK();i++){
                    functions.add(new LagerrIntegralTaoFunction(i,config.getAlfa(),config.getGamma()));
                }
                break;
        }


        JavaRDD<ITaoFunction> functionsRdd = context.parallelize(functions);
        System.out.println("******************************* COUNTING L *******************************");
        double delta = config.getDelta();
        double h=config.getH();
        start();

        JavaRDD<List<MatrixEntry>> map = functionsRdd.map(function -> CalculationUtils.getMatrixRow(function,delta,h).getValues());
        JavaRDD<MatrixEntry> flatten=map.flatMap(y->y.iterator());
        CoordinateMatrix matrix = new CoordinateMatrix(flatten.rdd());
        BlockMatrix matL = matrix.toBlockMatrix().cache();
        matL.validate();
        timeParameters.setTimeL(end());
        System.out.println("******************************* COUNTING L*L^1 *******************************");
        start();
        BlockMatrix matLTransp = matL.transpose();
        BlockMatrix multiply = matL.multiply(matLTransp);
        timeParameters.setTimeLonLt(end());

        start();
        System.out.println("******************************* SVD *******************************");
        IndexedRowMatrix indexedRowMatrix = multiply.toIndexedRowMatrix();
        long cols = indexedRowMatrix.numCols();
        SingularValueDecomposition<IndexedRowMatrix, Matrix> svd = indexedRowMatrix.computeSVD((int) cols, true, indexedRowMatrix.computeSVD$default$3());

        System.out.println("******************************* DIAG S *******************************");
        double[] doubles = svd.s().toArray();
        List<MatrixEntry> invSEntries = new ArrayList<>();
        for(int i = 0 ; i<doubles.length;i++){
            //doubles[i]=1/doubles[i];
            invSEntries.add(new MatrixEntry(i,i,1/doubles[i]));
        }
        CoordinateMatrix invSCoord = new CoordinateMatrix(context.parallelize(invSEntries).rdd());

        BlockMatrix invS = invSCoord.toBlockMatrix();
        BlockMatrix U = svd.U().toBlockMatrix();
        System.out.println("******************************* V TO BLOCK MATRIX *******************************");
        Matrix vDense = svd.V();
        double[] vDenseArray = vDense.toArray();
        List<MatrixEntry> vEntries=new ArrayList<>();
        for(int i = 0; i < vDense.numRows(); i ++){
            for(int j = 0 ; j < vDense.numCols();j++){
                vEntries.add(new MatrixEntry(i,j,vDenseArray[i*vDense.numCols()+j]));
            }
        }
        BlockMatrix V = new CoordinateMatrix(context.parallelize(vEntries).rdd()).toBlockMatrix();
        System.out.println("******************************* V*S*U *******************************");
        BlockMatrix result = (V.multiply(invS)).multiply(U.transpose());
        timeParameters.setTimeLonLtInverted(end());
        return result;
    }

    private void start(){
        start = System.currentTimeMillis();
    }
    private long end(){
        return System.currentTimeMillis()-start;
    }


}
