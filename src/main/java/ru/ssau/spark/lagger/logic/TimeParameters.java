package ru.ssau.spark.lagger.logic;

/**
 * Created by Dmitry on 14.12.2017.
 */
public class TimeParameters {
    private long timeLSeq;
    private long timeLonLtSeq;
    private long timeLonLtInvertedSeq;
    private long timeLParallel;
    private long timeLonLtParallel;
    private long timeLonLtInvertedParallel;


    public long getTimeLSeq() {
        return timeLSeq;
    }

    public void setTimeLSeq(long timeLSeq) {
        this.timeLSeq = timeLSeq;
    }

    public long getTimeLonLtSeq() {
        return timeLonLtSeq;
    }

    public void setTimeLonLtSeq(long timeLonLtSeq) {
        this.timeLonLtSeq = timeLonLtSeq;
    }

    public long getTimeLonLtInvertedSeq() {
        return timeLonLtInvertedSeq;
    }

    public void setTimeLonLtInvertedSeq(long timeLonLtInvertedSeq) {
        this.timeLonLtInvertedSeq = timeLonLtInvertedSeq;
    }

    public long getTimeLParallel() {
        return timeLParallel;
    }

    public void setTimeLParallel(long timeLParallel) {
        this.timeLParallel = timeLParallel;
    }

    public long getTimeLonLtParallel() {
        return timeLonLtParallel;
    }

    public void setTimeLonLtParallel(long timeLonLtParallel) {
        this.timeLonLtParallel = timeLonLtParallel;
    }

    public long getTimeLonLtInvertedParallel() {
        return timeLonLtInvertedParallel;
    }

    public void setTimeLonLtInvertedParallel(long timeLonLtInvertedParallel) {
        this.timeLonLtInvertedParallel = timeLonLtInvertedParallel;
    }

    @Override
    public String toString() {
        return "TimeParameters{" +
                "timeLSeq=" + timeLSeq +
                ", timeLonLtSeq=" + timeLonLtSeq +
                ", timeLonLtInvertedSeq=" + timeLonLtInvertedSeq +
                ", timeLParallel=" + timeLParallel +
                ", timeLonLtParallel=" + timeLonLtParallel +
                ", timeLonLtInvertedParallel=" + timeLonLtInvertedParallel +
                '}';
    }
}
