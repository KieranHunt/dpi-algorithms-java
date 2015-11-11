package casa.kieran.result;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.input.Input;
import casa.kieran.rule.Rules;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Created by kieran on 2015/11/10.
 */
public class Result {
    private Rules rules;
    private Input input;
    private Class algorithmClass;
    private Instant start;
    private Instant end;

    private ArrayList<Integer> locations;

    public Result(Rules rules, Input input, Algorithm algorithm) {
        this.rules = rules;
        this.input = input;
        this.algorithmClass = algorithm.getClass();

        this.locations = new ArrayList<>();
    }

    public void addLocation(Integer location) {
        this.locations.add(location);
    }

    public void start() {
        this.start = Instant.now();
    }

    public void end() {
        this.end = Instant.now();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        this.locations.forEach((location) -> stringBuffer.append(location + " "));

        return String.format("[%s]: %s - %s \n %d microseconds \n %s \n\n", this.algorithmClass.getCanonicalName(),
                this.rules,
                this.input, (this.end
                        .getNano() - this.start.getNano()) * 1000, stringBuffer.toString());
    }
}
