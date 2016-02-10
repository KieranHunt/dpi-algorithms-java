package casa.kieran.dpi.statistics.algorithm;

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

public class AlgorithmStatisticsGenerator extends AbstractSpecificStatisticsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmStatisticsGenerator.class);

    @Override
    public Map<String, Statistics> generateStatisticsMap(Results results) {

        Map<String, Statistics> algorithmStatisticsMap = new HashMap<>();

        results.getTest().getAlgorithms().forEach(algorithm -> {

            LOGGER.info(GENERATING_MESSAGE + "algorithm: " + algorithm);

            Filter filter = new Filter("algorithmClass", algorithm.getClass(), true);
            List<Result> resultList = ResultsFilterer.getFilteredResultList(results, filter);

            Statistics statistics = generateStatistics(resultList);

            algorithmStatisticsMap.put(algorithm.toString(), statistics);

        });

        return algorithmStatisticsMap;
    }
}
