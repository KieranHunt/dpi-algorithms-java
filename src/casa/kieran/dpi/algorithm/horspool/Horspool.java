package casa.kieran.dpi.algorithm.horspool;

import casa.kieran.dpi.algorithm.AbstractParallelizableAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.algorithm.boyermoore.BoyerMoore;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.List;
import java.util.Map;

public class Horspool extends AbstractParallelizableAlgorithm implements Algorithm {

    private static final int ALPHABET_SIZE = 256;

    private static Horspool instance;

    private Rules rules;

    private Map<Rule, List<Integer>> preBMBC;

    public static Horspool getInstance(Rules rules) {
        if (instance == null) {
            instance = new Horspool();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {

        this.rules = rules;

        preBMBC = BoyerMoore.preBmBc(rules);
    }


    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        int n = input.getLength();

        rules.forEach(rule -> {
            int j;
            byte c;

            List<Integer> bmBc = preBMBC.get(rule);

            int m = rule.getLength();

            j = 0;
            while (j <= n - m) {
                c = input.getByte(j + m - 1);
                if (rule.getByte(m - 1).equals(c) && memcmp(rule, input, 0, j, m - 1)) {
                    result.addLocation(j);
                }
                j += bmBc.get(c);
            }
        });

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Horspool";
    }
}
