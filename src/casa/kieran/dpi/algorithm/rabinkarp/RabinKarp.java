package casa.kieran.dpi.algorithm.rabinkarp;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the Karp-Rabin algorithm
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node5.html#SECTION0050
 */
public class RabinKarp extends AbstractAlgorithm {

    private static RabinKarp instance;

    private Rules rules;

    private Map<Rule, Integer> preD;

    private Map<Rule, Integer> preHY;

    private Map<Rule, Integer> preHX;

    private int seed;

    public static RabinKarp getInstance(Rules rules) {
        if (instance == null) {
            instance = new RabinKarp();
        }
        instance.changeRules(rules);
        return instance;
    }

    public void changeRules(Rules rules) {
        this.rules = rules;
        seed = Math.abs((new Random()).nextInt());
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        List<Runnable> runnables = new ArrayList<>();

        rules.forEach(rule -> {
            Runnable runnable = new RabinKarpRunnable(input, result, n, rule);
            runnables.add(runnable);
        });

        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    private class RabinKarpRunnable implements Runnable {
        private Input input;
        private Result result;
        private int n;
        private Rule rule;

        public RabinKarpRunnable(Input input, Result result, int n, Rule rule) {
            this.input = input;
            this.result = result;
            this.n = n;
            this.rule = rule;
        }

        public void run() {
            int hx, hy, j;

            int m = rule.getLength();

            hx = hash(rule, 0, m, seed);
            hy = hash(input, 0, m, seed);

            j = 0;
            while (j <= n - m) {
                if (hx == hy) {
                    if (memcmp(rule, input, 0, j, m)) {
                        result.addLocation(j);
                    }
                }
                j++;
                if (j + m > n) {
                    break;
                }
                hy = hash(input, j, m, seed);
            }
        }

        private int hash(Rule rule, int j, int m, int seed) {

            int hash = 0;

            for (int i = j; i < j + m; i++) {
                hash = (int) (hash + (rule.getByte(i) * Math.pow(2, i - j) % seed));
            }

            return hash;
        }

        private int hash(Input input, int j, int m, int seed) {

            int hash = 0;

            for (int i = j; i < j + m; i++) {
                hash = (int) (hash + (input.getByte(i) * Math.pow(2, i - j) % seed));
            }

            return hash;
        }
    }
}
