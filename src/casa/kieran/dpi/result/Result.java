package casa.kieran.dpi.result;

import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.rule.Rules;

import java.util.SortedSet;
import java.util.TreeSet;

public class Result {
    private Rules rules;
    private Input input;
    private Class algorithmClass;
    private Long start;
    private Long end;
    private int runNumber;
    private String runId;

    private SortedSet<Integer> locations;

    public Result(Rules rules, Input input, Algorithm algorithm, int runNumber, String runId) {
        this.rules = rules;
        this.input = input;
        this.algorithmClass = algorithm.getClass();
        this.runNumber = runNumber;
        this.runId = runId;

        this.locations = new TreeSet<>();
    }

    public void addLocation(Integer location) {
        if (start == null) {
            throw new RuntimeException("You need to start the timer first.");
        }
        this.locations.add(location);
    }

    public Long getElapsed() {
        return end - start;
    }

    public void start() {
        this.start = System.nanoTime();
    }

    public void end() {
        this.end = System.nanoTime();
    }

    public Rules getRules() {
        return rules;
    }

    public Input getInput() {
        return input;
    }

    public Class getAlgorithmClass() {
        return algorithmClass;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public SortedSet<Integer> getLocations() {
        return locations;
    }

    public String getRunId() {
        return runId;
    }

    @Override
    public String toString() {
        if (end == null) {
            throw new RuntimeException("You need to end the timer first.");
        }
        StringBuffer stringBuffer = new StringBuffer();
        this.locations.forEach((location) -> stringBuffer.append(location + " "));

        return String.format("%s,%s,%s,%s,%d nanoseconds,%s\n",
                this.runNumber,
                this.algorithmClass.getSimpleName(),
                this.rules,
                this.input, (this.end - this.start),
                stringBuffer.toString());
    }
}
