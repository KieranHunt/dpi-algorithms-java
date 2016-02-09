package casa.kieran.dpi.statistics.testrun;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.statistics.AbstractStatisticsGenerator;
import casa.kieran.dpi.statistics.OverallStatistics;
import casa.kieran.dpi.statistics.Statistics;
import casa.kieran.dpi.statistics.filter.Filter;
import casa.kieran.dpi.statistics.filter.ResultsFilterer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TestRunStatisticsGenerator extends AbstractStatisticsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunStatisticsGenerator.class);

    public static OverallStatistics generateTestRunStatistics(Results results) {
        OverallStatistics overallStatistics = new OverallStatistics();

        results.getTest().getRunIds().forEach(runId -> {

            LOGGER.info(GENERATING_MESSAGE + "runId: " +  runId);

            Filter filter = new Filter("runId", runId, true);
            List<Result> resultList = ResultsFilterer.getFilteredResultList(results, filter);

            Statistics statistics = generateStatistics(resultList);

            overallStatistics.addStatistics(runId, statistics);
        });

        List<Result> resultList = new ArrayList<>();
        resultList.addAll(results.getResults());

        Statistics statistics = generateStatistics(resultList);

        overallStatistics.setOverAllStatistics(statistics);

        return overallStatistics;
    }
}
