package casa.kieran.dpi.statistics.testrun;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.result.filter.Filter;
import casa.kieran.dpi.result.filter.ResultsFilterer;
import casa.kieran.dpi.statistics.AbstractSpecificStatisticsGenerator;
import casa.kieran.dpi.statistics.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRunStatisticsGenerator extends AbstractSpecificStatisticsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunStatisticsGenerator.class);

    @Override
    public Map<String, Statistics> generateStatisticsMap(Results results) {
        Map<String, Statistics> testRunStatisticsMap = new HashMap<>();

        results.getTest().getRunIds().forEach(runId -> {

            LOGGER.info(GENERATING_MESSAGE + "runId: " + runId);

            Filter filter = new Filter("runId", runId, true);
            List<Result> resultList = ResultsFilterer.getFilteredResultList(results, filter);

            Statistics statistics = generateStatistics(resultList);

            testRunStatisticsMap.put(runId, statistics);
        });

        return testRunStatisticsMap;
    }

}