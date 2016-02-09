package casa.kieran;

import casa.kieran.dpi.statistics.OverallStatistics;
import casa.kieran.dpi.statistics.algorithm.AlgorithmStatisticsGenerator;
import casa.kieran.dpi.statistics.inputfile.InputFileStatisticsGenerator;
import casa.kieran.dpi.statistics.testrun.TestRunStatisticsGenerator;
import casa.kieran.dpi.test.Test;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        DateTime start = DateTime.now();

        LOGGER.info("------------------------------");
        LOGGER.info("DPI Algorithm Benchmark System");
        LOGGER.info("By Kieran Hunt");
        LOGGER.info("------------------------------\n");

        String configurationLocation;

        if (args.length == 0) {
            LOGGER.info("No test configuration location supplied, using default.");
            configurationLocation = "resources/testConfiguration.json";
        } else {
            LOGGER.info("Using test configuration found at " + args[0]);
            configurationLocation = args[0];
        }

        Test test = new Test(configurationLocation);

        test.run();

        DateTime end = DateTime.now();
        Period elapsed = new Period(start, end);
        LOGGER.info("Total time elapsed: " + elapsed.getSeconds() + "s");

        OverallStatistics overallStatistics = AlgorithmStatisticsGenerator
                .generateAlgorithmStatistics(test.getResults());

        OverallStatistics overallStatistics2 = TestRunStatisticsGenerator
                .generateTestRunStatistics(test.getResults());

        OverallStatistics overallStatistics3 = InputFileStatisticsGenerator
                .generateInputFileStatistics(test.getResults());

        System.out.println("");

    }
}