package casa.kieran.dpi.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A POJO representing the configuration for a test.
 * Used to read out of a configuration file.
 */
public class TestConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConfiguration.class);

    private List<String> algorithms;
    private List<String> rules;
    private List<TestInputs> inputs;
    private int times;

    public TestConfiguration() {

    }

    public TestConfiguration(List<String> algorithms, List<String> rules, List<TestInputs> inputs, int times) {
        this.algorithms = algorithms;
        this.rules = rules;
        this.inputs = inputs;
        this.times = times;
    }

    public static class TestInputs {
        private String type;
        private String location;

        public TestInputs() {

        }

        public TestInputs(String type, String location) {
            this.type = type;
            this.location = location;
        }

        public String getType() {
            return type;
        }

        public String getLocation() {
            return location;
        }
    }

    public List<String> getAlgorithms() {
        return algorithms;
    }

    public List<String> getRules() {
        return rules;
    }

    public List<TestInputs> getInputs() {
        return inputs;
    }

    public int getTimes() {
        return times;
    }
}
