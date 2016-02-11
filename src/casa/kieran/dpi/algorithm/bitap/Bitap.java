package casa.kieran.dpi.algorithm.bitap;

import casa.kieran.dpi.algorithm.AbstractParallelizableAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.List;

public class Bitap extends AbstractParallelizableAlgorithm implements Algorithm {

    private static Bitap instance;

    private Rules rules;

    public static Bitap getInstance(Rules rules) {
        if (instance == null) {
            instance = new Bitap();
        }
        instance.rules = rules;
        return instance;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        Integer textLength = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        for (Rule rule :
                rules) {
            Runnable runnable = new BitapRunnable(input, result, textLength, rule);
            runnables.add(runnable);
        }

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Bitap";
    }

    private class BitapRunnable implements Runnable {
        private Input input;
        private Result result;
        private Integer textLength;
        private Rule rule;

        public BitapRunnable(Input input, Result result, Integer textLength, Rule rule) {
            this.input = input;
            this.result = result;
            this.textLength = textLength;
            this.rule = rule;
        }

        public void run() {
            Integer ruleLength = rule.getLength();

            if (ruleLength == 0) {
                result.addLocation(0);
            }

            boolean[] r = new boolean[ruleLength + 1];
            r[0] = true;

            for (int i = 0; i < textLength; i++) {
                for (int j = ruleLength; j >= 1; j--) {
                    r[j] = r[j - 1] && (input.getByte(i) == rule.getByte(j - 1));
                }
                if (r[ruleLength]) {
                    result.addLocation(i - ruleLength + 1);
                }
            }
        }
    }
}
