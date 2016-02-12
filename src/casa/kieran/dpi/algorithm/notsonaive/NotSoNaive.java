package casa.kieran.dpi.algorithm.notsonaive;

import casa.kieran.dpi.algorithm.AbstractParallelizableAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

public class NotSoNaive extends AbstractParallelizableAlgorithm implements Algorithm {

    private static NotSoNaive instance;

    private Rules rules;

    public static NotSoNaive getInstance(Rules rules) {
        if (instance == null) {
            instance = new NotSoNaive();
        }
        instance.changeRules(rules);
        return instance;
    }

    public void changeRules(Rules rules) {
        this.rules = rules;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        rules.getRules().forEach(rule -> {
            int j, k, ell;

            int m = rule.getLength();

            if (m == 1) {
                k = 1;
                ell = 2;
            } else {
                if (rule.getByte(0).equals(rule.getByte(1))) {
                    k = 2;
                    ell = 1;
                } else {
                    k = 1;
                    ell = 2;
                }
            }

            j = 0;
            while (j <= n - m) {
                if (!rule.getByte(1).equals(input.getByte(j + 1))) {
                    j += k;
                } else {
                    if (memcmp(rule, input, 2, j + 2, m - 2) && rule.getByte(0).equals(input.getByte(j))) {
                        result.addLocation(j);
                    }
                    j += ell;
                }
            }
        });

        result.end();
        results.addResult(result);
    }

    private boolean memcmp(Rule rule, Input input, int i, int j, int n) {
        for (int k = 0; k < n; k++) {
            if (!input.getByte(j + k).equals(rule.getByte(i + k))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "NotSoNaive";
    }
}
