package casa.kieran.dpi.algorithm.quicksearch;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuickSearch extends AbstractAlgorithm {

    private static QuickSearch instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preQSBC;

    public static QuickSearch getInstance(Rules rules) {
        if (instance == null) {
            instance = new QuickSearch();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {

        this.rules = rules;
        this.preQSBC = preQsBc(rules);

    }

    public static Map<Rule, List<Integer>> preQsBc(Rules rules) {
        Map<Rule, List<Integer>> preQsBc = new HashMap<>();

        rules.forEach(rule -> {
            int i;

            int m = rule.getLength();

            List<Integer> qsBc = new ArrayList<>(Collections.nCopies(ALPHABET_SIZE, 0));

            for (i = 0; i < ALPHABET_SIZE; ++i) {
                qsBc.set(i, m + 1);
            }
            for (i = 0; i < m; ++i) {
                qsBc.set(rule.getByte(i), m - i);
            }

            preQsBc.put(rule, qsBc);
        });

        return preQsBc;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.forEach(rule -> {
            Runnable runnable = new QuickSearchRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    private class QuickSearchRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public QuickSearchRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int j;

            int m = rule.getLength();

            List<Integer> qsBc = preQSBC.get(rule);

            j = 0;
            while (j <= n - m) {
                if (memcmp(rule, input, 0, j, m)) {
                    result.addLocation(j);
                }
                j += qsBc.get(input.getByte(j + m));
            }
        }
    }
}
