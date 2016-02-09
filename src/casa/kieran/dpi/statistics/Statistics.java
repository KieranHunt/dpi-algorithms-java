package casa.kieran.dpi.statistics;

public class Statistics {

    private int count;
    private long min;
    private long max;
    private double mean;
    private double standardDeviation;

    public Statistics(int count, long min, long max, double mean, double standardDeviation) {
        this.count = count;
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    public Statistics() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
