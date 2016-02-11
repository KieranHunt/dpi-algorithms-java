package casa.kieran.dpi.algorithm.colussi;

import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the Colussi Algorithm
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node10.html
 */
public class Colussi implements Algorithm {

    private static Colussi instance;

    private Rules rules;

    public static Colussi getInstance(Rules rules) {
        if (instance == null) {
            instance = new Colussi();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        rules.forEach(rule -> {

            int m = rule.getLength();

            int i;
            int k;
            int nd;
            int q;
            int r;
            int s;

            List<Integer> hmax = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> kmin = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> nhd0 = new ArrayList<>(Collections.nCopies(m, 0));
            List<Integer> rmin = new ArrayList<>(Collections.nCopies(m, 0));

            //Calculate hmax
            i = k = 1;
            do {
                while (rule.getByte(i).equals(rule.getByte(i - k))) {
                    i++;
                }
                hmax.set(k, i);
                q = k + 1;
                while (hmax.get(q - k) + 1 < i) {
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

            /**
             * Currently not working. r hasn't been initialized
             */

            //Calculate rmin
            for (i = m; i >= 0; --i) {
                if (hmax.get(i + 1) == m) {
                    r = i + 1;
                }
                if (kmin.get(i) == 0) {
//                    rmin.set(i, r);
                } else {
                    rmin.set(i, 0);
                }
            }
        });
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {

    }
}
