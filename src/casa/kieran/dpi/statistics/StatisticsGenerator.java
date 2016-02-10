package casa.kieran.dpi.statistics;

import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.statistics.algorithm.AlgorithmStatisticsGenerator;
import casa.kieran.dpi.statistics.all.AllStatisticsGenerator;
import casa.kieran.dpi.statistics.inputfile.InputFileStatisticsGenerator;
import casa.kieran.dpi.statistics.testrun.TestRunStatisticsGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StatisticsGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsGenerator.class);

    private AlgorithmStatisticsGenerator algorithmStatisticsGenerator = new AlgorithmStatisticsGenerator();
    private TestRunStatisticsGenerator testRunStatisticsGenerator = new TestRunStatisticsGenerator();
    private InputFileStatisticsGenerator inputFileStatisticsGenerator = new InputFileStatisticsGenerator();
    private AllStatisticsGenerator allStatisticsGenerator = new AllStatisticsGenerator();

    private Results results;

    public StatisticsGenerator(Results results) {
        this.results = results;
    }

    public OverallStatistics generateStatistics() {

        LOGGER.info("Generating All Statistics");

        Map<String, Statistics> algorithmStatisticsMap = algorithmStatisticsGenerator.generateStatisticsMap(results);
        Map<String, Statistics> testRunStatisticsMap = testRunStatisticsGenerator.generateStatisticsMap(results);
        Map<String, Statistics> inputFileStatisticsMap = inputFileStatisticsGenerator.generateStatisticsMap(results);

        Statistics allStatistics = allStatisticsGenerator.generateAllStatistics(results);

        OverallStatistics overallStatistics = new OverallStatistics(
                algorithmStatisticsMap,
                testRunStatisticsMap,
                inputFileStatisticsMap,
                allStatistics);

        return overallStatistics;
    }
}
