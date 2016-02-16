package casa.kieran.dpi.algorithm.turboboyermoore;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
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
 * Implementation of the TurboBoyerMoore algorithm
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node15.html
 */
public class TurboBoyerMoore extends AbstractAlgorithm implements Algorithm {
    private static TurboBoyerMoore instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preBmBc;

    private Map<Rule, List<Integer>> preBmGs;

    public static TurboBoyerMoore getInstance(Rules rules) {
        if (instance == null) {
            instance = new TurboBoyerMoore();
        }
        instance.rules = rules;
        instance.preBmBc = BoyerMoore.preBmBc(rules);
        instance.preBmGs = BoyerMoore.preBmGs(rules);
        return instance;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.forEach(rule -> {
            Runnable runnable = new TurboBoyerMooreRannable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "TurboBoyerMoore";
    }

    private class TurboBoyerMooreRannable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public TurboBoyerMooreRannable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            List<Integer> bmGs = preBmGs.get(rule);
            List<Integer> bmBc = preBmBc.get(rule);

            int m = rule.getLength();

            int bcShift, i, j, shift, u, v, turboShift;

            j = u = 0;
            shift = m;
            while (j <= n - m) {
                i = m - 1;
                while (i >= 0 && rule.getByte(i).equals(input.getByte(i + j))) {
                    --i;
                    if (u != 0 && i == m - 1 - shift) {
                        i -= u;
                    }
                }
                if (i < 0) {
                    result.addLocation(j);
                    shift = bmGs.get(0);
                    u = m - shift;
                } else {
                    v = m - 1 - i;
                    turboShift = u - v;
                    bcShift = bmBc.get(input.getByte(i + j)) - m + 1 + i;
                    shift = Math.max(turboShift, bcShift);
                    shift = Math.max(shift, bmGs.get(i));
                    if (shift == bmGs.get(i)) {
                        u = Math.min(m - shift, v);
                    } else {
                        if (turboShift < bcShift) {
                            shift = Math.max(shift, u + 1);
                        }
                        u = 0;
                    }
                }
                j += shift;
            }
        }
    }
}
