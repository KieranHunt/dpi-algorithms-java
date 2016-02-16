package casa.kieran.dpi.algorithm.zhutakaoka;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
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

public class ZhuTakaoka extends AbstractAlgorithm implements Algorithm {

    private static ZhuTakaoka instance;

    private Rules rules;

    private Map<Rule, List<List<Integer>>> preZTBC;

    private Map<Rule, List<Integer>> preBMGS;

    public static ZhuTakaoka getInstance(Rules rules) {
        if (instance == null) {
            instance = new ZhuTakaoka();
        }
        instance.changeRules(rules);
        return instance;
    }

    public void changeRules(Rules rules) {
        this.rules = rules;
        preZTBC = preZTBC(rules);
        preBMGS = BoyerMoore.preBmGs(rules);
    }

    private Map<Rule, List<List<Integer>>> preZTBC(Rules rules) {
        Map<Rule, List<List<Integer>>> preZTBC = new HashMap<>();

        rules.forEach(rule -> {
            List<List<Integer>> ztBc = new ArrayList<>(Collections.nCopies(ALPHABET_SIZE, new ArrayList<>()));
            for (int i = 0; i < ztBc.size(); i++) {
                ztBc.set(i, new ArrayList<>(Collections.nCopies(ALPHABET_SIZE, 0)));
            }

            int m = rule.getLength();

            int i, j;

            for (i = 0; i < ALPHABET_SIZE; ++i) {
                for (j = 0; j < ALPHABET_SIZE; ++j) {
                    ztBc.get(i).set(j, m);
                }
            }
            for (i = 0; i < ALPHABET_SIZE; ++i) {
                ztBc.get(i).set(rule.getByte(0), m - 1);
            }
            for (i = 1; i < m - 1; ++i) {
                ztBc.get(rule.getByte(i - 1)).set(rule.getByte(i), m - 1 - i);
            }

            preZTBC.put(rule, ztBc);
        });

        return preZTBC;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();
        rules.forEach(rule -> {
            Runnable runnable = new ZhuTakaokaRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);
        result.end();
        results.addResult(result);
    }

    private class ZhuTakaokaRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public ZhuTakaokaRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int i, j;

            List<List<Integer>> ztBc = preZTBC.get(rule);
            List<Integer> bmGs = preBMGS.get(rule);

            int m = rule.getLength();

            j = 0;
            while (j <= n - m) {
                i = m - 1;
                while (i < m && rule.getByte(i).equals(input.getByte(i + j))) {
                    --i;
                    if (i < 0) {
                        break;
                    }
                }
                if (i < 0) {
                    result.addLocation(j);
                    j += bmGs.get(0);
                } else {
                    j += Math.max(bmGs.get(i), ztBc.get(input.getByte(j + m - 2)).get(input.getByte(j + m - 1)));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "ZhuTakaoka";
    }
}
