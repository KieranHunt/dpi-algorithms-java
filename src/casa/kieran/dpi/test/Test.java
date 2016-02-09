package casa.kieran.dpi.test;

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

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    private static final int DEFAULT_TEST_TIMES = 1;

    private Algorithms algorithms;
    private Rules rules;
    private Inputs inputs;
    private int times;

    private Results results;

    /**
     * Create a new test object using all of the pre-created elements.
     *
     * @param algorithms - the algorithms to be tested
     * @param rules      - the rules to match
     * @param inputs     - the input files to test
     * @param times      - the number of times to run the tests
     */
    public Test(Algorithms algorithms, Rules rules, Inputs inputs, int times) {
        this.algorithms = algorithms;
        this.rules = rules;
        this.inputs = inputs;
        this.times = times;
        results = new Results();
    }

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

        results = new Results();
    }

    public void run() {
        LOGGER.info("Starting test");
        for (int run = 0; run < times; run++) {
            int runNumber = run + 1;
            LOGGER.info("Running test #" + runNumber);
            for (Algorithm algorithm : algorithms) {
                LOGGER.info("Performing search with " + algorithm + " algorithm");
                for (Input input : inputs) {
                    algorithm.search(input, results);
                }
            }
        }
        LOGGER.info("Test complete");
    }

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

    private int readTimesFromTestConfiguration(TestConfiguration testConfiguration) {
        LOGGER.info("Checking for number of test times");
        int times = times = ((Integer) testConfiguration.getTimes() != null) ? testConfiguration.getTimes() :
                DEFAULT_TEST_TIMES;
        LOGGER.info("Setting test times to " + times);
        return times;
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

    public Results getResults() {
        return results;
    }
}
