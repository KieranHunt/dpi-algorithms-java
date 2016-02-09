package casa.kieran.dpi.statistics.algorithm;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.statistics.AbstractStatisticsGenerator;
import casa.kieran.dpi.statistics.OverallStatistics;
import casa.kieran.dpi.statistics.Statistics;
import casa.kieran.dpi.statistics.filter.Filter;
import casa.kieran.dpi.statistics.filter.ResultsFilterer;

import java.util.List;

public class AlgorithmStatisticsGenerator extends AbstractStatisticsGenerator {

    public static OverallStatistics generateAlgorithmStatistics(Results results) {
        OverallStatistics overallStatistics = new OverallStatistics();

        results.getTest().getAlgorithms().forEach(algorithm -> {

            Filter filter = new Filter("algorithm", algorithm.getClass(), true);

            List<Result> resultList = ResultsFilterer.getFilteredResultList(results, filter);

            int count = resultList.size();

            long totalTime = 0;
            long max = Long.MAX_VALUE;
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
                    new Statistics(algorithm.getClass(), resultList.size(), min, max, mean, standardDeviation);

            overallStatistics.getStatisticsList().add(statistics);
        });



    }
}
