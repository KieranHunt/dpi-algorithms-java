package casa.kieran.dpi.algorithm.smith;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.boyermoore.BoyerMoore;
import casa.kieran.dpi.algorithm.quicksearch.QuickSearch;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Smith extends AbstractAlgorithm {

    private static Smith instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preQSBC;
    private Map<Rule, List<Integer>> preBMBC;

    public static Smith getInstance(Rules rules) {
        if (instance == null) {
            instance = new Smith();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {

        this.rules = rules;

        preQSBC = QuickSearch.preQsBc(rules);
        preBMBC = BoyerMoore.preBmBc(rules);

    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.forEach(rule -> {
            Runnable runnable = new SmithRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Smith";
    }

    private class SmithRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public SmithRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int m = rule.getLength();

            List<Integer> qsBc = preQSBC.get(rule);
            List<Integer> bmBc = preBMBC.get(rule);

            int j = 0;

            while (j <= n - m) {
                if (memcmp(rule, input, 0, j, m)) {
                    result.addLocation(j);
                }
                j += Math.max(bmBc.get(input.getByte(j + m - 1)), qsBc.get(input.getByte(j + m)));
            }
        }
    }
}
