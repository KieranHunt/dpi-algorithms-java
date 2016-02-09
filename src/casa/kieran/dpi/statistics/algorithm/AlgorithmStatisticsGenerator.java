package casa.kieran.dpi.statistics.algorithm;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.statistics.AbstractStatisticsGenerator;
import casa.kieran.dpi.statistics.OverallStatistics;
import casa.kieran.dpi.statistics.Statistics;
import casa.kieran.dpi.statistics.filter.Filter;
import casa.kieran.dpi.statistics.filter.ResultsFilterer;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmStatisticsGenerator extends AbstractStatisticsGenerator {

    public static OverallStatistics generateAlgorithmStatistics(Results results) {
        OverallStatistics overallStatistics = new OverallStatistics();

        results.getTest().getAlgorithms().forEach(algorithm -> {

            Filter filter = new Filter("algorithmClass", algorithm.getClass(), true);
            List<Result> resultList = ResultsFilterer.getFilteredResultList(results, filter);

            Statistics statistics = generateStatistics(resultList);

            overallStatistics.addStatistics(algorithm.getClass(), statistics);

        });

        List<Result> resultList = new ArrayList<>();
        resultList.addAll(results.getResults());

        Statistics statistics = generateStatistics(resultList);

        overallStatistics.setOverAllStatistics(statistics);

        return overallStatistics;
    }
}
