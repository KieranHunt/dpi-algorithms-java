package casa.kieran;

import casa.kieran.dpi.statistics.OverallStatistics;
import casa.kieran.dpi.statistics.StatisticsGenerator;
import casa.kieran.dpi.statistics.writer.StatisticsWriter;
import casa.kieran.dpi.test.Test;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        DateTime startTests = DateTime.now();

        LOGGER.info("------------------------------");
        LOGGER.info("DPI Algorithm Benchmark System");
        LOGGER.info("By Kieran Hunt");
        LOGGER.info("------------------------------\n");

        String configurationLocation;

        if (args.length == 0) {
            LOGGER.warn("No test configuration location supplied, using default.");
            configurationLocation = "resources/testConfiguration.json";
        } else {
            LOGGER.info("Using test configuration found at " + args[0]);
            configurationLocation = args[0];
        }

        Test test = new Test(configurationLocation);

        test.run();

        DateTime endTests = DateTime.now();
        Period elapsedTests = new Period(startTests, endTests);
        LOGGER.info("Total time for tests elapsed: " + elapsedTests.getSeconds() + "s\n");
        DateTime startStatistics = DateTime.now();

        StatisticsGenerator statisticsGenerator = new StatisticsGenerator(test.getResults());

        OverallStatistics overallStatistics = statisticsGenerator.generateStatistics();
        StatisticsWriter.writeStatistics(overallStatistics);

        DateTime endStatistics = DateTime.now();
        Period elapsedStatistics = new Period(startStatistics, endStatistics);
        LOGGER.info("Total time for statistics elapsed: " + elapsedStatistics.getSeconds() + "s\n");

        LOGGER.info("------------------------------");
        LOGGER.info("Testing and Analysing Complete");
        LOGGER.info("------------------------------\n");

        System.out.println(test.getResults());
    }
}