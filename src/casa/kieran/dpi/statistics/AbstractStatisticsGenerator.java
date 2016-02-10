package casa.kieran.dpi.statistics;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractStatisticsGenerator {

    protected static final String GENERATING_MESSAGE = "Generating statistics for ";

    public static Statistics generateStatistics(Results results) {
        List<Result> resultList = new ArrayList<>();
        resultList.addAll(results.getResults());
        return generateStatistics(resultList);
    }

    public static Statistics generateStatistics(List<Result> resultList) {
        int count = resultList.size();

        long totalTime = 0;
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;

        for (Result result : resultList) {
            long elapsed = result.getElapsed();

            totalTime = totalTime + elapsed;

            if (elapsed > max) {
                max = elapsed;
            }

            if (elapsed < min) {
                min = elapsed;
            }

        }

        double mean = totalTime / count;

        double totalSquareDifferences = 0;

        for (Result result : resultList) {
            totalSquareDifferences += Math.pow((result.getElapsed() - mean), 2);
        }

        double standardDeviation = Math.sqrt(totalSquareDifferences / count);

        Statistics statistics =
                new Statistics(resultList.size(), min, max, mean, standardDeviation);

        return statistics;
    }
}
