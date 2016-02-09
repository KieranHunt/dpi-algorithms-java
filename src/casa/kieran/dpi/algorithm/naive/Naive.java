package casa.kieran.dpi.algorithm.naive;

import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Naive implements Algorithm {

    Logger LOGGER = LoggerFactory.getLogger(Naive.class);

    private static Naive instance;

    private Rules rules;

    private Naive(Rules rules) {
        this.rules = rules;
    }

    public static Naive getInstance(Rules rules) {
        if (instance == null) {
            return new Naive(rules);
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {

        Result result = new Result(this.rules, input, this, runNumber, runId);
        result.start();

        for (Rule rule :
                this.rules) {
            for (int i = 0; i <= input.getLength() - rule.getLength(); i++) {
                int j;
                for (j = 0; j < rule.getLength(); j++) {
                    if (!input.getByte(i + j).equals(rule.getByte(j))) {
                        break;
                    }
                }
                if (j == rule.getLength()) {
                    result.addLocation(i);
                }
            }
        }

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Naïve";
    }
}
