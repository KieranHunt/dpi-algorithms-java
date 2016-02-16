package casa.kieran.dpi.algorithm.notsonaive;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.List;

public class NotSoNaive extends AbstractAlgorithm implements Algorithm {

    private static NotSoNaive instance;

    private Rules rules;

    public static NotSoNaive getInstance(Rules rules) {
        if (instance == null) {
            instance = new NotSoNaive();
        }
        instance.changeRules(rules);
        return instance;
    }

    public void changeRules(Rules rules) {
        this.rules = rules;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>(results.getResults().size());

        rules.getRules().forEach(rule -> {
            Runnable runnable = new NotSoNaiveRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "NotSoNaive";
    }

    private class NotSoNaiveRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public NotSoNaiveRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int j, k, ell;

            int m = rule.getLength();

            if (m == 1) {
                k = 1;
                ell = 2;
            } else {
                if (rule.getByte(0).equals(rule.getByte(1))) {
                    k = 2;
                    ell = 1;
                } else {
                    k = 1;
                    ell = 2;
                }
            }

            j = 0;
            while (j <= n - m) {
                if (!rule.getByte(1).equals(input.getByte(j + 1))) {
                    j += k;
                } else {
                    if (memcmp(rule, input, 2, j + 2, m - 2) && rule.getByte(0).equals(input.getByte(j))) {
                        result.addLocation(j);
                    }
                    j += ell;
                }
            }
        }
    }
}
