package casa.kieran.dpi.algorithm.galilgiancarlo;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.colussi.Colussi;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalilGiancarlo extends AbstractAlgorithm {
    private static GalilGiancarlo instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preH;
    private Map<Rule, List<Integer>> preNEXT;
    private Map<Rule, List<Integer>> preSHIFT;
    private Map<Rule, Integer> preND;

    public static GalilGiancarlo getInstance(Rules rules) {
        if (instance == null) {
            instance = new GalilGiancarlo();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {

        this.rules = rules;

        preH = new HashMap<>();
        preNEXT = new HashMap<>();
        preSHIFT = new HashMap<>();
        preND = new HashMap<>();

        Colussi.preColussi(rules, preH, preNEXT, preSHIFT, preND);
    }

    @Override
    public String toString() {
        return "GalilGiancarlo";
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.forEach(rule -> {
            Runnable runnable = new GalilGiancarloRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    private class GalilGiancarloRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public GalilGiancarloRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int i, j, k, ell, last, nd;

            List<Integer> h = preH.get(rule);
            List<Integer> next = preNEXT.get(rule);
            List<Integer> shift = preSHIFT.get(rule);

            boolean heavy;

            int m = rule.getLength();

            for (ell = 0; rule.getByte(ell).equals(rule.getByte(ell + 1)); ell++) ;

            if (ell == m - 1) {
                for (j = ell = 0; j < n; ++j) {
                    if (rule.getByte(0).equals(input.getByte(j))) {
                        ++ell;
                        if (ell >= m) {
                            result.addLocation(j - m + 1);
                        }
                    } else {
                        ell = 0;
                    }
                }
            } else {
                nd = preND.get(rule);

                i = j = 0;
                heavy = false;
                last = -1;
                while (j <= n - m) {
                    if (heavy && i == 0) {
                        k = last - j + 1;
                        while (rule.getByte(0).equals(input.getByte(j + k))) {
                            k++;
                        }
                        if (k <= ell || !rule.getByte(ell + 1).equals(input.getByte(j + k))) {
                            i = 0;
                            j += k + 1;
                            last = j - 1;
                        } else {
                            i = 1;
                            last = j + k;
                            j = last - ell + 1;
                        }
                        heavy = false;
                    } else {
                        while (i < m && last < j + h.get(i)
                                && rule.getByte(h.get(i)).equals(input.getByte(j + h.get(i)))) {
                            ++i;
                        }
                        if (i >= m || last >= j + h.get(i)) {
                            result.addLocation(j);
                            i = m;
                        }
                        if (i > nd) {
                            last = j + m - 1;
                        }
                        j += shift.get(i);
                        i = next.get(i);
                    }
                    heavy = !(j > last);
                }
            }
        }
    }
}
