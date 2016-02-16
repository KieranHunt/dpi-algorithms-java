package casa.kieran.dpi.algorithm.apostolicogiancarlo;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.boyermoore.BoyerMoore;
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

public class ApostolicoGiancarlo extends AbstractAlgorithm {
    private static ApostolicoGiancarlo instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preBmBc;

    private Map<Rule, List<Integer>> preBmGs;

    private Map<Rule, List<Integer>> suffixes;

    private Map<Rule, List<Integer>> preSkip;

    public static ApostolicoGiancarlo getInstance(Rules rules) {
        if (instance == null) {
            instance = new ApostolicoGiancarlo();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        instance.rules = rules;
        instance.preBmBc = BoyerMoore.preBmBc(rules);
        instance.preBmGs = BoyerMoore.preBmGs(rules);
        instance.suffixes = BoyerMoore.suffixes(rules);

        preSkip = new HashMap<>();

        rules.forEach(rule -> {
            preSkip.put(rule, new ArrayList<>(Collections.nCopies(rule.getLength(), 0)));
        });
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.getRules().forEach(rule -> {
            Runnable runnable = new ApostolicoGiancarloRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    private class ApostolicoGiancarloRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public ApostolicoGiancarloRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int i, j, k, s, shift;

            List<Integer> bmBc = preBmBc.get(rule);
            List<Integer> bmGs = preBmGs.get(rule);
            List<Integer> suff = suffixes.get(rule);
            List<Integer> skip = preSkip.get(rule);

            int m = rule.getLength();

            j = 0;
            while (j <= n - m) {
                i = m - 1;
                while (i >= 0) {
                    k = skip.get(i);
                    s = suff.get(i);
                    if (k > 0) {
                        if (k > s) {
                            if (i + 1 == s) {
                                i = -1;
                            } else {
                                i -= s;
                            }
                            break;
                        } else {
                            i -= k;
                            if (k < s) {
                                break;
                            }
                        }
                    } else {
                        if (rule.getByte(i).equals(input.getByte(i + j))) {
                            --i;
                        } else {
                            break;
                        }
                    }
                }
                if (i < 0) {
                    result.addLocation(j);
                    skip.set(m - 1, m);
                    shift = bmGs.get(0);
                } else {
                    skip.set(m - 1, m - 1 - i);
                    shift = Math.max(bmGs.get(i), bmBc.get(input.getByte(i + j)) - m + 1 + i);
                }
                j += shift;
                memcpy(skip, shift, m - shift);
                memset(skip, m - shift, skip.size(), 0);
            }
        }
    }

    @Override
    public String toString() {
        return "ApostolicoGiancarlo";
    }
}
