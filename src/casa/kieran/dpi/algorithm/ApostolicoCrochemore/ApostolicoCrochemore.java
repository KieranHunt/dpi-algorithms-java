package casa.kieran.dpi.algorithm.apostolicocrochemore;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.knuthmorrispratt.KnuthMorrisPratt;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the Apostolico-Crochemore algorithm
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node12.html#SECTION00120
 */
public class ApostolicoCrochemore extends AbstractAlgorithm {

    private static ApostolicoCrochemore instance;

    private Rules rules;

    private Map<Rule, int[]> table;

    public static ApostolicoCrochemore getInstance(Rules rules) {
        if (instance == null) {
            instance = new ApostolicoCrochemore();
        }
        instance.changeRules(rules);
        return instance;
    }

    public void changeRules(Rules rules) {
        this.rules = rules;
        table = KnuthMorrisPratt.precompute(rules);
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.getRules().forEach(rule -> {
            Runnable runnable = new ApostolicoCrochemoreRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "ApostolicoCrochemore";
    }

    private class ApostolicoCrochemoreRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public ApostolicoCrochemoreRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int m = rule.getLength();

            int[] kmpNext = table.get(rule);

            int i;
            int j;
            int k;
            int ell;

            for (ell = 1; rule.getByte(ell - 1).equals(rule.getByte(ell)); ell++) ;

            if (ell == m) {
                ell = 0;
            }

            i = ell;
            j = k = 0;

            while (j <= n - m) {
                while (i < m && rule.getByte(i).equals(input.getByte(i + j))) {
                    ++i;
                }
                if (i >= m) {
                    while (k < ell && rule.getByte(k).equals(input.getByte(j + k))) {
                        ++k;
                    }
                    if (k >= ell) {
                        result.addLocation(j);
                    }
                }
                j += (i - kmpNext[i]);
                if (i == ell) {
                    k = Math.max(0, k - 1);
                } else {
                    if (kmpNext[i] <= ell) {
                        k = Math.max(0, kmpNext[i]);
                        i = ell;
                    } else {
                        k = ell;
                        i = kmpNext[i];
                    }
                }
            }
        }
    }
}
