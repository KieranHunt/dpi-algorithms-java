package casa.kieran.dpi.algorithm.reversecolussi;

import casa.kieran.dpi.algorithm.AbstractParallelizableAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
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
 * Implementation of the Reverse Colussi algorithm.
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node17.html
 */
public class ReverseColussi extends AbstractParallelizableAlgorithm implements Algorithm {

    private static final int ALPHABET_SIZE = 256;

    private static ReverseColussi instance;

    private Rules rules;

    private Map<Rule, List<List<Integer>>> preRCBC;

    private Map<Rule, List<Integer>> preRCGS;

    private Map<Rule, List<Integer>> preH;

    public static ReverseColussi getInstance(Rules rules) {
        if (instance == null) {
            instance = new ReverseColussi();
        }
        instance.changeRules(rules);
        return instance;
    }

    @Override
    public String toString() {
        return "ReverseColussi";
    }

    private void changeRules(Rules rules) {

        this.rules = rules;

        preRCBC = new HashMap<>();
        preRCGS = new HashMap<>();
        preH = new HashMap<>();

        rules.forEach(rule -> {
            int a, i, j, k, q, r, s;

            r = 0;

            int m = rule.getLength();

            List<List<Integer>> rcBc = new ArrayList<>(ALPHABET_SIZE);
            List<Integer> rcGs = new ArrayList<>(Collections.nCopies(m + 1, 0));
            List<Integer> h = new ArrayList<>(Collections.nCopies(m, 0));

            List<Integer> hmin = new ArrayList<>(Collections.nCopies(m + 1, 0));
            List<Integer> kmin = new ArrayList<>(Collections.nCopies(m + 1, 0));
            List<Integer> link = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> locc = new ArrayList<>(Collections.nCopies(ALPHABET_SIZE, 0));
            List<Integer> rmin = new ArrayList<>(Collections.nCopies(m + 1, 0));

            for (int z = 0; z < ALPHABET_SIZE; z++) {
                List<Integer> characterList = new ArrayList<>(Collections.nCopies(m + 1, 0));
                rcBc.add(characterList);
            }

            //Calculate link and locc
            for (a = 0; a < ALPHABET_SIZE; ++a) {
                locc.set(a, -1);
            }
            link.set(0, -1);
            for (i = 0; i < m - 1; ++i) {
                link.set(i + 1, locc.get(rule.getByte(i)));
                locc.set(rule.getByte(i), i);
            }

            //Calculate rcBc
            for (a = 0; a < ALPHABET_SIZE; ++a) {
                for (s = 1; s <= m; ++s) {
                    i = locc.get(a);
                    j = link.get(m - s);
                    while (i - j != s && j >= 0) {
                        if (i - j > s) {
                            i = link.get(i + 1);
                        } else {
                            j = link.get(j + 1);
                        }
                    }
                    while (i - j > s) {
                        i = link.get(i + 1);
                    }
                    rcBc.get(a).set(s, m - i - 1);
                }
            }

            //Calculate hmin
            k = 1;
            i = m - 1;
            while (k <= m) {
                while (i - k >= 0 && rule.getByte(i - k).equals(rule.getByte(i))) {
                    --i;
                }
                hmin.set(k, i);
                q = k + 1;
                while (hmin.get(q - k) - (q - k) > i) {
                    hmin.set(q, hmin.get(q - k));
                    ++q;
                }
                i += (q - k);
                k = q;
                if (i == m) {
                    i = m - 1;
                }
            }

            //Calculate kmin
            for (k = m; k > 0; --k) {
                kmin.set(hmin.get(k), k);
            }

            //Calculate rmin
            for (i = m - 1; i >= 0; --i) {
                if (hmin.get(i + 1) == i) {
                    r = i + 1;
                }
                rmin.set(i, r);
            }

            //Calculate rcGs
            i = 1;
            for (k = 1; k <= m; ++k) {
                if (hmin.get(k) != m - 1 && kmin.get(hmin.get(k)) == k) {
                    h.set(i, hmin.get(k));
                    rcGs.set(i++, k);
                }
            }
            i = m - 1;
            for (j = m - 2; j >= 0; --j) {
                if (kmin.get(j) == 0) {
                    h.set(i, j);
                    rcGs.set(i--, rmin.get(j));
                }
            }
            rcGs.set(m, rmin.get(0));

            preH.put(rule, h);
            preRCGS.put(rule, rcGs);
            preRCBC.put(rule, rcBc);
        });
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        List<Runnable> runnables = new ArrayList<>();

        int n = input.getLength();

        rules.forEach(rule -> {
            Runnable runnable = new ReverseColussiRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    private class ReverseColussiRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public ReverseColussiRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int m = rule.getLength();

            List<List<Integer>> rcBc = preRCBC.get(rule);
            List<Integer> rcGs = preRCGS.get(rule);
            List<Integer> h = preH.get(rule);

            int i, j, s;

            j = 0;
            s = m;
            while (j <= n - m) {
                while (j <= n - m && !rule.getByte(m - 1).equals(input.getByte(j + m - 1))) {
                    s = rcBc.get(input.getByte(j + m - 1)).get(s);
                    j += s;
                }
                for (i = 1; i < m && rule.getByte(h.get(i)).equals(input.getByte(j + h.get(i))); ++i) ;
                if (i >= m) {
                    if (j + m < n) {
                        result.addLocation(j);
                    }
                }
                s = rcGs.get(i);
                j += s;
            }
        }
    }
}
