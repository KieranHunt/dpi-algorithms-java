package casa.kieran.dpi.algorithm.naive;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Naive extends AbstractAlgorithm implements Algorithm {

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

        List<Runnable> runnables = new ArrayList<>();

        for (Rule rule :
                this.rules) {
            Runnable runnable = new NaiveRunnable(input, result, rule);
            runnables.add(runnable);
        }

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Naïve";
    }

    private class NaiveRunnable implements Runnable {
        private Input input;
        private Result result;
        private Rule rule;

        public NaiveRunnable(Input input, Result result, Rule rule) {
            this.input = input;
            this.result = result;
            this.rule = rule;
        }

        public void run() {
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
    }
}
