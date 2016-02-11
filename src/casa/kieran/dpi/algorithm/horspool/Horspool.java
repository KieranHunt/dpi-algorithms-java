package casa.kieran.dpi.algorithm.horspool;

import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Horspool implements Algorithm {

    private static final int ALPHABET_SIZE = 256;

    private static Horspool instance;

    private Rules rules;

    private Map<Rule, List<Integer>> table;

    public static Horspool getInstance(Rules rules) {
        if (instance == null) {
            instance = new Horspool();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {

        this.rules = rules;

        table = new HashMap<>();

        rules.forEach(rule -> {

            int m = rule.getLength();

            List<Integer> positions = new ArrayList<>(ALPHABET_SIZE);

            int i;

            for (i = 0; i < ALPHABET_SIZE; ++i) {
                positions.set(i, m);
            }
            for (i = 0; i < m - 1; ++i) {
                positions.set(rule.getByte(i), m - i - 1);
            }
            table.put(rule, positions);
        });
    }


    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        rules.forEach(rule -> {

        });
    }
}
