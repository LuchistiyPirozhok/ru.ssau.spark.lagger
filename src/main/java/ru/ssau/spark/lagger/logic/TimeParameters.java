package ru.ssau.spark.lagger.logic;

/**
 * Created by Dmitry on 14.12.2017.
 */
public class TimeParameters {

    private long timeL;
    private long timeLonLt;
    private long timeLonLtInverted;


    public long getTimeL() {
        return timeL;
    }

    public void setTimeL(long timeL) {
        this.timeL = timeL;
    }

    public long getTimeLonLt() {
        return timeLonLt;
    }

    public void setTimeLonLt(long timeLonLt) {
        this.timeLonLt = timeLonLt;
    }

    public long getTimeLonLtInverted() {
        return timeLonLtInverted;
    }

    public void setTimeLonLtInverted(long timeLonLtInvertedParallel) {
        this.timeLonLtInverted = timeLonLtInvertedParallel;
    }

    @Override
    public String toString() {
        return "TimeParameters{" +
                " timeL=" + timeL +
                ", timeLonLt=" + timeLonLt +
                ", timeLonLtInverted=" + timeLonLtInverted +
                '}';
    }
}
