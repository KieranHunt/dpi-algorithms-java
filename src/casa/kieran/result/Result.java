package casa.kieran.result;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.input.Input;
import casa.kieran.rule.Rules;

import java.util.HashSet;
import java.util.Set;

public class Result {
    private Rules rules;
    private Input input;
    private Class algorithmClass;
    private Long start;
    private Long end;

    private Set<Integer> locations;

    public Result(Rules rules, Input input, Algorithm algorithm) {
        this.rules = rules;
        this.input = input;
        this.algorithmClass = algorithm.getClass();

        this.locations = new HashSet<>();
    }

    public void addLocation(Integer location) {
        if (start == null) {
            throw new RuntimeException("You need to start the timer first.");
        }
        this.locations.add(location);
    }

    public void start() {
        this.start = System.nanoTime();
    }

    public void end() {
        this.end = System.nanoTime();
    }

    @Override
    public String toString() {
        if (end == null) {
            throw new RuntimeException("You need to end the timer first.");
        }
        StringBuffer stringBuffer = new StringBuffer();
        this.locations.forEach((location) -> stringBuffer.append(location + " "));

        return String.format("%s,%s,%s,%d nanoseconds,%s\n",
                this.algorithmClass.getSimpleName(),
                this.rules,
                this.input, (this.end - this.start),
                stringBuffer.toString());
    }
}
