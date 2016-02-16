package casa.kieran.dpi.test;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.algorithm.AlgorithmFactory;
import casa.kieran.dpi.algorithm.Algorithms;
import casa.kieran.dpi.exception.UnimplementedAlgorithmException;
import casa.kieran.dpi.exception.UnimplementedInputTypeException;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.input.Inputs;
import casa.kieran.dpi.input.InputsFactory;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Test {

    private String testId;

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    private static final int DEFAULT_TEST_TIMES = 1;

    private Algorithms algorithms;
    private Rules rules;
    private Inputs inputs;
    private int times;
    private int threadCount;

    private List<String> runIds = new ArrayList<>();
    private Set<String> testFileLocations = new HashSet<>();

    private Results results;

    /**
     * Create a new test object from a configuration file.
     *
     * @param configurationLocation - the path to the configuration file.
     */
    public Test(String configurationLocation) {
        TestConfiguration testConfiguration = readTestConfiguration(configurationLocation);

        rules = readRulesFromTestConfiguration(testConfiguration);
        algorithms = readAlgorithmsFromTestConfiguration(testConfiguration, rules);
        inputs = readInputsFromTestConfiguration(testConfiguration);
        times = readTimesFromTestConfiguration(testConfiguration);
        threadCount = readThreadCountFromTestConfiguration(testConfiguration);

        algorithms.forEach(algorithm -> {
            ((AbstractAlgorithm) algorithm).setThreadCount(threadCount);
        });

        testId = Integer.toHexString((new Random()).nextInt());

        results = new Results(this);
    }


    public void run() {
        String message =
                String.format("Starting test with %s input(s), %s rule(s) and %s algorithm(s). Running %s time(s). " +
                                "This will generate %s result objects.",
                        inputs.getNumberOfInputs(), rules.getNumberOfRules(), algorithms.getNumberOfAlgorithms(),
                        times, inputs.getNumberOfInputs() * rules.getNumberOfRules() *
                                algorithms.getNumberOfAlgorithms() * times);
        LOGGER.info("------------------------------");
        LOGGER.info("Starting Testing");
        LOGGER.info("------------------------------\n");
        LOGGER.info(message);
        for (int run = 0; run < times; run++) {
            int runNumber = run + 1;
            String runId = generateRunId();
            runIds.add(runId);
            LOGGER.info("Running test " + runNumber + "/" + times + " with ID " + runId);
            for (Algorithm algorithm : algorithms) {
                LOGGER.info("Performing search with " + algorithm + " algorithm");
                String location = "";
                for (Input input : inputs) {

                    if (input.getLocation() != location) {
                        location = input.getLocation();
                        LOGGER.info("Searching through " + location);
                    }

                    testFileLocations.add(input.getLocation());
                    algorithm.search(input, results, runNumber, runId);
                }
            }
        }
        LOGGER.info("Test complete");
    }

    private String generateRunId() {
        return UUID.randomUUID().toString().split("-")[4];
    }

    /**
     * Read the Rules from a TestConfiguration object
     *
     * @param testConfiguration - the object to read the rules from.
     * @return the rules read from the TestConfiguration object
     */
    private Rules readRulesFromTestConfiguration(TestConfiguration testConfiguration) {
        LOGGER.info("Checking for rules");
        Rules rules = new Rules();
        testConfiguration.getRules().forEach(ruleString -> {
            Rule rule = new Rule(ruleString);
            rules.addRule(rule);
        });
        LOGGER.info("Found " + rules.getNumberOfRules() + " rule(s)");
        return rules;
    }

    /**
     * Read the Algorithms from the TestConfiguration object
     *
     * @param testConfiguration - the object containing the algorithms
     * @param rules             - the object containing the rules for the algorithms
     * @return the algorithms created from the TestConfiguration object
     */
    private Algorithms readAlgorithmsFromTestConfiguration(TestConfiguration testConfiguration, Rules rules) {
        Algorithms algorithms = new Algorithms();
        LOGGER.info("Checking for algorithms");
        testConfiguration.getAlgorithms().forEach(algorithmString -> {
            try {
                Algorithm algorithm = AlgorithmFactory.getAlgorithm(algorithmString, rules);
                algorithms.addAlgorithm(algorithm);
                LOGGER.info("Found Algorithm: " + algorithm);
            } catch (UnimplementedAlgorithmException e) {
                e.printStackTrace();
            }
        });
        LOGGER.info("Found " + algorithms.getNumberOfAlgorithms() + " algorithm(s)");
        return algorithms;
    }

    /**
     * Read the Inputs from the TestConfiguration object
     *
     * @param testConfiguration - the test configuration object to read the inputs out of.
     * @return the Inputs from the TestConfiguration object.
     */
    private Inputs readInputsFromTestConfiguration(TestConfiguration testConfiguration) {
        Inputs inputs = new Inputs();
        LOGGER.info("Checking for Inputs");
        testConfiguration.getInputs().forEach(testInputs -> {
            try {
                Inputs inputsReadIn = InputsFactory.getInputs(testInputs.getType(), testInputs.getLocation());
                inputs.addInputs(inputsReadIn);
            } catch (UnimplementedInputTypeException e) {
                e.printStackTrace();
            }
        });
        LOGGER.info("Found " + inputs.getNumberOfInputs() + " input(s)");
        return inputs;
    }

    /**
     * Read the times from the TestConfiguration object
     *
     * @param testConfiguration - the TestConfiguration object to have the times read from.
     * @return the number of times to run the tests, from the TestConfiguration object
     */
    private int readTimesFromTestConfiguration(TestConfiguration testConfiguration) {
        LOGGER.info("Checking for number of test times");
        int times = ((Integer) testConfiguration.getTimes() != null) ? testConfiguration.getTimes() :
                DEFAULT_TEST_TIMES;
        LOGGER.info("Setting test times to " + times);
        return times;
    }

    /**
     * Read the maximum number of threads to use.
     *
     * @param testConfiguration - the TestConfiguration object to have the times read from.
     * @return the maximum number of threads to use.
     */
    private int readThreadCountFromTestConfiguration(TestConfiguration testConfiguration) {
        LOGGER.info("Checking for number of threads to use");
        int count = ((Integer) testConfiguration.getThreadCount() != null) ? testConfiguration.getThreadCount() :
                AbstractAlgorithm.DEFAULT_THREAD_COUNT;
        LOGGER.info("Setting thread count to " + count + "\n");
        return count;
    }

    /**
     * Reads in test configuration from a file.
     *
     * @param testConfigurationLocation - the location of the test configuration file.
     * @return the read in test configuration
     */
    private TestConfiguration readTestConfiguration(final String testConfigurationLocation) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TestConfiguration testConfiguration = mapper.readValue(new File(testConfigurationLocation),
                    TestConfiguration.class);
            LOGGER.info("Found test configuration at " + testConfigurationLocation);
            return testConfiguration;

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Could not read test configuration file", e);
        }
        return null;
    }

    public Algorithms getAlgorithms() {
        return algorithms;
    }

    public Rules getRules() {
        return rules;
    }

    public Inputs getInputs() {
        return inputs;
    }

    public int getTimes() {
        return times;
    }

    public Results getResults() {
        return results;
    }

    public List<String> getRunIds() {
        return runIds;
    }

    public Set<String> getTestFileLocations() {
        return testFileLocations;
    }

    public String getTestId() {
        return testId;
    }
}
