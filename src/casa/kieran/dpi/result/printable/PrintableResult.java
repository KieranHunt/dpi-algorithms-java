package casa.kieran.dpi.result.printable;

import java.util.List;
import java.util.Set;

public class PrintableResult {
    private long start;
    private long end;
    private long elapsed;
    private List<String> rules;
    private Set<Integer> locations;
    private String algorithm;
    private String inputFile;
    private String inputID;
    private int runNumber;
    private String runId;
    private long inputLength;

    public PrintableResult(long start,
                           long end,
                           long elapsed,
                           List<String> rules,
                           Set<Integer> locations,
                           String algorithm,
                           String inputFile,
                           String inputID,
                           int runNumber,
                           String runId,
                           long inputLength) {
        this.start = start;
        this.end = end;
        this.elapsed = elapsed;
        this.rules = rules;
        this.locations = locations;
        this.algorithm = algorithm;
        this.inputFile = inputFile;
        this.inputID = inputID;
        this.runNumber = runNumber;
        this.runId = runId;
        this.inputLength = inputLength;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
    }

    public List<String> getRules() {
        return rules;
    }

    public void setRules(List<String> rules) {
        this.rules = rules;
    }

    public Set<Integer> getLocations() {
        return locations;
    }

    public void setLocations(Set<Integer> locations) {
        this.locations = locations;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getInputID() {
        return inputID;
    }

    public void setInputID(String inputID) {
        this.inputID = inputID;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public long getInputLength() {
        return inputLength;
    }

    public void setInputLength(long inputLength) {
        this.inputLength = inputLength;
    }
}
