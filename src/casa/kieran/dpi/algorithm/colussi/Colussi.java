package casa.kieran.dpi.algorithm.colussi;

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

/**
 * Implementation of the Colussi Algorithm
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node10.html
 */
public class Colussi extends AbstractAlgorithm {

    private static Colussi instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preH;
    private Map<Rule, List<Integer>> preNEXT;
    private Map<Rule, List<Integer>> preSHIFT;
    private Map<Rule, Integer> preND;

    public static Colussi getInstance(Rules rules) {
        if (instance == null) {
            instance = new Colussi();
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

        preColussi(rules, preH, preNEXT, preSHIFT, preND);
    }

    @Override
    public String toString() {
        return "Colussi";
    }

    public static void preColussi(Rules rules,
                                  Map<Rule, List<Integer>> preH,
                                  Map<Rule, List<Integer>> preNEXT,
                                  Map<Rule, List<Integer>> preSHIFT,
                                  Map<Rule, Integer> preND) {
        rules.forEach(rule -> {

            int m = rule.getLength();

            int i;
            int k;
            int nd;
            int q;
            int r = 0;
            int s;

            List<Integer> hmax = new ArrayList<>(Collections.nCopies(m + 1, 0));
            List<Integer> kmin = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> nhd0 = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> rmin = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> h = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> shift = new ArrayList<>(Collections.nCopies(m + 1, 0));
            List<Integer> next = new ArrayList<>(Collections.nCopies(m + 1, 0));

            //Calculate hmax
            i = k = 1;
            do {
                try {
                    while (rule.getByte(i).equals(rule.getByte(i - k))) {
                        i++;
                    }
                } catch (Exception e) {
                }
                hmax.set(k, i);
                q = k + 1;
                while (hmax.get(q - k) + k < i) {
                    hmax.set(q, hmax.get(q - k) + k);
                    q++;
                }
                k = q;
                if (k == i + 1) {
                    i = k;
                }
            } while (k <= m);

            //Calculate kmin
            for (i = m - 1; i >= 0; --i) {
                if (hmax.get(i) < m) {
                    kmin.set(hmax.get(i), i);
                }
            }

            //Calculate rmin
            for (i = m - 1; i >= 0; --i) {
                if (hmax.get(i + 1) == m) {
                    r = i + 1;
                }
                if (kmin.get(i) == 0) {
                    rmin.set(i, r);
                } else {
                    rmin.set(i, 0);
                }
            }

            //Calculate h
            s = -1;
            r = m;
            for (i = 0; i < m; ++i) {
                if (kmin.get(i) == 0) {
                    h.set(--r, i);
                } else {
                    h.set(++s, i);
                }
            }
            nd = s;

            //Calculate shift
            for (i = 0; i <= nd; ++i) {
                shift.set(i, kmin.get(h.get(i)));
            }
            for (i = nd + 1; i < m; ++i) {
                shift.set(i, rmin.get(h.get(i)));
            }
            shift.set(m, rmin.get(0));

            //Calculate nhd0
            s = 0;
            for (i = 0; i < m; ++i) {
                nhd0.set(i, s);
                if (kmin.get(i) > 0) {
                    ++s;
                }
            }

            //Calculate next
            for (i = 0; i <= nd; ++i) {
                next.set(i, nhd0.get(h.get(i) - kmin.get(h.get(i))));
            }
            for (i = nd + 1; i < m; ++i) {
                next.set(i, nhd0.get(m - rmin.get(h.get(i))));
            }
            next.set(m, nhd0.get(m - rmin.get(h.get(m - 1))));

            preH.put(rule, h);
            preNEXT.put(rule, next);
            preSHIFT.put(rule, shift);
            preND.put(rule, nd);
        });
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        List<Runnable> runnables = new ArrayList<>();

        int n = input.getLength();

        rules.forEach(rule -> {
            Runnable runnable = new ColussiRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    private class ColussiRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public ColussiRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int i, j, last, nd;

            List<Integer> h = preH.get(rule);
            List<Integer> next = preNEXT.get(rule);
            List<Integer> shift = preSHIFT.get(rule);

            nd = preND.get(rule);

            int m = rule.getLength();

            i = j = 0;
            last = -1;
            while (j <= n - m) {
                while (i < m && last < j + h.get(i) && rule.getByte(h.get(i)).equals(input.getByte(j + h.get(i)))) {
                    i++;
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
        }
    }
}
