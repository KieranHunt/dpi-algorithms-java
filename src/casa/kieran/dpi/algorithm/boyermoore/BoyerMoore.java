package casa.kieran.dpi.algorithm.boyermoore;

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

public class BoyerMoore extends AbstractAlgorithm {

    private static final int ALPHABET_SIZE = 256;

    private static BoyerMoore instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preBmBc;

    private Map<Rule, List<Integer>> suffixes;

    private Map<Rule, List<Integer>> preBmGs;

    public static BoyerMoore getInstance(Rules rules) {
        if (instance == null) {
            instance = new BoyerMoore();
        }
        instance.rules = rules;
        instance.preBmBc = instance.preBmBc(rules);
        instance.suffixes = instance.suffixes(rules);
        instance.preBmGs = instance.preBmGs(rules);
        return instance;
    }

    public static Map<Rule, List<Integer>> preBmBc(Rules rules) {

        Map<Rule, List<Integer>> preBmBc = new HashMap<>();

        rules.forEach(rule -> {

            List<Integer> bmbc = new ArrayList<>(Collections.nCopies(ALPHABET_SIZE, 0));

            int m = rule.getLength();

            int i;

            for (i = 0; i < ALPHABET_SIZE; i++) {
                bmbc.set(i, m);
            }
            for (i = 0; i < m - 1; ++i) {
                bmbc.set(rule.getByte(i), m - i - 1);
            }

            preBmBc.put(rule, bmbc);
        });

        return preBmBc;
    }

    public static Map<Rule, List<Integer>> suffixes(Rules rules) {
        Map<Rule, List<Integer>> suffixes = new HashMap<>();

        rules.forEach(rule -> {

            int m = rule.getLength();

            List<Integer> suff = new ArrayList<>(Collections.nCopies(m, 0));

            int f, g, i;

            f = 0;

            suff.set(m - 1, m);
            g = m - 1;
            for (i = m - 2; i >= 0; --i) {
                if (i > g && suff.get(i + m - 1 - f) < i - g) {
                    suff.set(i, suff.get(i + m - 1 - f));
                } else {
                    if (i < g) {
                        g = i;
                    }
                    f = i;
                    while (g >= 0 && rule.getByte(g).equals(rule.getByte(g + m - 1 - f))) {
                        --g;
                    }
                    suff.set(i, f - g);
                }
            }

            suffixes.put(rule, suff);
        });

        return suffixes;
    }

    public static Map<Rule, List<Integer>> preBmGs(Rules rules) {
        Map<Rule, List<Integer>> preBmGs = new HashMap<>();

        Map<Rule, List<Integer>> suffixes = suffixes(rules);

        rules.forEach(rule -> {

            int m = rule.getLength();

            List<Integer> bmGs = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> suff = suffixes.get(rule);

            int i, j;

            for (i = 0; i < m; ++i) {
                bmGs.set(i, m);
            }
            j = 0;
            for (i = m - 1; i >= 0; --i) {
                if (suff.get(i).equals(i + 1)) {
                    for (; j < m - 1 - i; ++j) {
                        if (bmGs.get(j).equals(m)) {
                            bmGs.set(j, m - 1 - i);
                        }
                    }
                }
            }
            for (i = 0; i <= m - 2; ++i) {
                bmGs.set(m - 1 - suff.get(i), m - 1 - i);
            }

            preBmGs.put(rule, bmGs);
        });

        return preBmGs;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        List<Runnable> runnables = new ArrayList<>();

        for (Rule rule :
                rules) {
            Runnable runnable = new BoyerMooreRunnable(input, result, rule);
            runnables.add(runnable);
        }

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }


    @Override
    public String toString() {
        return "Boyer-Moore";
    }

    private class BoyerMooreRunnable implements Runnable {
        private Input input;
        private Result result;
        private Rule rule;

        public BoyerMooreRunnable(Input input, Result result, Rule rule) {
            this.input = input;
            this.result = result;
            this.rule = rule;
        }

        public void run() {
            List<Integer> bmGs = preBmGs.get(rule);
            List<Integer> bmBc = preBmBc.get(rule);

            int n = input.getLength();
            int m = rule.getLength();

            int i, j;

            j = 0;
            while (j <= n - m) {
                i = m - 1;
                while (i >= 0 && rule.getByte(i).equals(input.getByte(i + j))) {
                    --i;
                }
                if (i < 0) {
                    result.addLocation(j);
                    j += bmGs.get(0);
                } else {
                    j += Math.max(bmGs.get(i), bmBc.get(input.getByte(i + j)) - m + 1 + i);
                }
            }
        }
    }
}
