package casa.kieran.dpi.algorithm.knuthmorrispratt;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KnuthMorrisPratt extends AbstractAlgorithm {

    private static KnuthMorrisPratt instance;

    private Rules rules;

    private Map<Rule, int[]> table;

    private KnuthMorrisPratt(Rules rules) {
        this.rules = rules;
        this.table = precompute(this.rules);
    }

    public static KnuthMorrisPratt getInstance(Rules rules) {
        if (instance == null) {
            instance = new KnuthMorrisPratt(rules);
            return instance;
        }
        instance.changeRules(rules);
        return instance;
    }

    /**
     * Construct the precomputed Knuth-Morris-Pratt table.
     *
     * @param rules The Rules object containing all of the rules.
     * @return A HashMap of Rule to Integer Array representing the table for each rule.
     */
    public static Map<Rule, int[]> precompute(Rules rules) {
        Map<Rule, int[]> table = new HashMap<>();
        for (Rule rule :
                rules) {

            int m = rule.getLength();

            int[] kmpNext = new int[m + 1];

            int i, j;

            i = 0;
            j = kmpNext[0] = -1;
            while (i < m) {
                while (j > -1 && !rule.getByte(i).equals(rule.getByte(j))) {
                    j = kmpNext[j];
                }
                i++;
                j++;
                try {
                    if (rule.getByte(i).equals(rule.getByte(j))) {
                        kmpNext[i] = kmpNext[j];
                    } else {
                        kmpNext[i] = j;
                    }
                } catch (RuntimeException e) {
                    kmpNext[i] = j;
                }
            }
            table.put(rule, kmpNext);
        }
        return table;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;
        this.table = precompute(this.rules);
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(this.rules, input, this, runNumber, runId);
        result.start();

        List<Runnable> runnableList = new ArrayList<>();

        for (Rule rule :
                this.rules) {
            Runnable runnable = new KnuthMorrisPrattRunnable(input, result, rule);
            runnableList.add(runnable);
        }

        executeSearch(runnableList);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Knuth-Morris-Pratt";
    }

    private class KnuthMorrisPrattRunnable implements Runnable {
        private Input input;
        private Result result;
        private Rule rule;

        public KnuthMorrisPrattRunnable(Input input, Result result, Rule rule) {
            this.input = input;
            this.result = result;
            this.rule = rule;
        }

        @Override
        public void run() {
            int i, j;

            int[] kmpNext = table.get(rule);
            int n = input.getLength();
            int m = rule.getLength();

            i = j = 0;
            while (j < n) {
                while (i > -1 && !rule.getByte(i).equals(input.getByte(j))) {
                    i = kmpNext[i];
                }
                i++;
                j++;
                if (i >= m) {
                    result.addLocation(j - i);
                    i = kmpNext[i];
                }
            }
        }
    }
}
