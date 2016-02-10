package casa.kieran.dpi.statistics.inputfile;

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

public class InputFileStatisticsGenerator extends AbstractSpecificStatisticsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputFileStatisticsGenerator.class);

    @Override
    public Map<String, Statistics> generateStatisticsMap(Results results) {

        Map<String, Statistics> inputFileStatisticsMap = new HashMap<>();

        results.getTest().getTestFileLocations().parallelStream().forEach(inputFileLocation -> {

            LOGGER.info(GENERATING_MESSAGE + "file: " + inputFileLocation);

            Filter filter = new Filter("inputLocation", inputFileLocation, true);
            List<Result> resultList = ResultsFilterer.getFilteredResultList(results, filter);

            Statistics statistics = generateStatistics(resultList);

            inputFileStatisticsMap.put(inputFileLocation, statistics);
        });

        return inputFileStatisticsMap;
    }
}
