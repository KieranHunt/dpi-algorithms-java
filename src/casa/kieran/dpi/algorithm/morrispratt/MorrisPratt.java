package casa.kieran.dpi.algorithm.morrispratt;

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
 * The Morris-Pratt Algorithm:
 * <p>
 * Source: http://www-igm.univ-mlv.fr/~lecroq/string/node7.html
 */
public class MorrisPratt implements Algorithm {

    private static MorrisPratt instance;

    private Rules rules;

    private Map<Rule, List<Integer>> table;

    public static MorrisPratt getInstance(Rules rules) {
        if (instance == null) {
            instance = new MorrisPratt();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;
        table = new HashMap<>();

        rules.forEach(rule -> {

            int m = rule.getLength();

            List<Integer> integers = new ArrayList<>(Collections.nCopies(m + 1, 0));

            int i = 0;
            int j = -1;
            integers.set(0, -1);

            while (i < m) {
                while (j > -1 && !rule.getByte(i).equals(rule.getByte(j))) {
                    j = integers.get(j);
                }
                integers.set(++i, ++j);
            }

            table.put(rule, integers);
        });
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        rules.forEach(rule -> {
            List<Integer> table = this.table.get(rule);

            int n = input.getLength();
            int m = rule.getLength();

            int i = 0;
            int j = 0;

            while (j < n) {
                while (i > -1 && !rule.getByte(i).equals(input.getByte(j))) {
                    i = table.get(i);
                }
                i++;
                j++;
                if (i >= m) {
                    result.addLocation(j - i);
                    i = table.get(i);
                }
            }
        });

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Morris-Pratt";
    }
}
