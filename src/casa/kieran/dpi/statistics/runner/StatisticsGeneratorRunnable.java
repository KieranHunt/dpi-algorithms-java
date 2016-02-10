package casa.kieran.dpi.statistics.runner;

import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.statistics.AbstractSpecificStatisticsGenerator;
import casa.kieran.dpi.statistics.Statistics;

import java.util.Map;

public class StatisticsGeneratorRunnable implements Runnable {

    private AbstractSpecificStatisticsGenerator abstractSpecificStatisticsGenerator;

    private Results results;

    private Map<String, Statistics> abstractSpecificStatisticsMap;

    public StatisticsGeneratorRunnable(AbstractSpecificStatisticsGenerator abstractSpecificStatisticsGenerator,
                                       Results results) {
        this.abstractSpecificStatisticsGenerator = abstractSpecificStatisticsGenerator;
        this.results = results;
    }

    @Override
    public void run() {
        abstractSpecificStatisticsMap = abstractSpecificStatisticsGenerator.generateStatisticsMap(results);
    }

    public Map<String, Statistics> getAbstractSpecificStatisticsMap() {
        return abstractSpecificStatisticsMap;
    }
}
