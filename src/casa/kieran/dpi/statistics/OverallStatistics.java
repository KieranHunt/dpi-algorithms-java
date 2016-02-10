package casa.kieran.dpi.statistics;

import java.util.Map;

public class OverallStatistics {

    private Map<String, Statistics> algorithmStatistics;
    private Map<String, Statistics> testRunStatistics;
    private Map<String, Statistics> inputFileStatistics;

    private Statistics overallStatistics;

    public OverallStatistics(Map<String, Statistics> algorithmStatistics, Map<String, Statistics> testRunStatistics, Map<String, Statistics> inputFileStatistics, Statistics overallStatistics) {
        this.algorithmStatistics = algorithmStatistics;
        this.testRunStatistics = testRunStatistics;
        this.inputFileStatistics = inputFileStatistics;
        this.overallStatistics = overallStatistics;
    }

    public Map<String, Statistics> getAlgorithmStatistics() {
        return algorithmStatistics;
    }

    public void setAlgorithmStatistics(Map<String, Statistics> algorithmStatistics) {
        this.algorithmStatistics = algorithmStatistics;
    }

    public Map<String, Statistics> getTestRunStatistics() {
        return testRunStatistics;
    }

    public void setTestRunStatistics(Map<String, Statistics> testRunStatistics) {
        this.testRunStatistics = testRunStatistics;
    }

    public Map<String, Statistics> getInputFileStatistics() {
        return inputFileStatistics;
    }

    public void setInputFileStatistics(Map<String, Statistics> inputFileStatistics) {
        this.inputFileStatistics = inputFileStatistics;
    }

    public Statistics getOverallStatistics() {
        return overallStatistics;
    }

    public void setOverallStatistics(Statistics overallStatistics) {
        this.overallStatistics = overallStatistics;
    }
}
