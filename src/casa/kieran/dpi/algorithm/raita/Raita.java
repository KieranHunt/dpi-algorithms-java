package casa.kieran.dpi.algorithm.raita;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.boyermoore.BoyerMoore;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Raita algorithm
 */
public class Raita extends AbstractAlgorithm {

    private static Raita instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preBMBC;

    public static Raita getInstance(Rules rules) {
        if (instance == null) {
            instance = new Raita();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {

        this.rules = rules;

        preBMBC = BoyerMoore.preBmBc(rules);

    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.forEach(rule -> {
            Runnable runnable = new RaitaRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Raita";
    }

    private class RaitaRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public RaitaRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int m = rule.getLength();

            List<Integer> bmBc = preBMBC.get(rule);

            int j;
            byte c, firstCh, secondCh, middleCh, lastCh;

            int secondCharacterLocation = m == 1 ? 0 : 1;

            firstCh = rule.getByte(0);
            secondCh = rule.getByte(secondCharacterLocation);
            middleCh = rule.getByte(m / 2);
            lastCh = rule.getByte(m - 1);

            j = 0;
            while (j <= n - m) {
                c = input.getByte(j + m - 1);
                if (c == lastCh && middleCh == input.getByte(j + m / 2) && firstCh == input.getByte(j)) {
                    if (memcmp(rule, input, secondCharacterLocation, j + 1, m - 2)) {
                        result.addLocation(j);
                    }
                }
                j += bmBc.get(c);
            }
        }
    }
}
