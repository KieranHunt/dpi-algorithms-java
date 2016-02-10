package casa.kieran.dpi.statistics.all;

import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.statistics.AbstractStatisticsGenerator;
import casa.kieran.dpi.statistics.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllStatisticsGenerator extends AbstractStatisticsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllStatisticsGenerator.class);

    public Statistics generateAllStatistics(Results results) {

        LOGGER.info("Generating Statistics for every result");

        return generateStatistics(results);
    }
}
