package casa.kieran.algorithm;

import casa.kieran.input.Input;
import casa.kieran.result.Result;
import casa.kieran.result.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

import java.util.HashMap;

/**
 * Created by kieran on 2015/11/06.
 */
public class KnuthMorrisPratt implements Algorithm {

    private static KnuthMorrisPratt instance;

    private Rules rules;

    private HashMap<Rule, Integer[]> table;

    private KnuthMorrisPratt(Rules rules) {
        this.rules = rules;
        this.table = precompute(this.rules);
    }

    public static KnuthMorrisPratt getInstance(Rules rules) {
        if (instance == null) {
            instance = new KnuthMorrisPratt(rules);
            return instance;
        }
        instance.changeRules(rules);
        return instance;
    }

    /**
     * Construct the precomputed Knuth-Morris-Pratt table.
     *
     * @param rules The Rules object containing all of the rules.
     * @return A HashMap of Rule to Integer Array representing the table for each rule.
     */
    private HashMap<Rule, Integer[]> precompute(Rules rules) {
        HashMap<Rule, Integer[]> table = new HashMap<>();
        for (Rule rule :
                rules) {
            Integer[] ruleTable = new Integer[rule.getLength()];
            ruleTable[0] = 0;
            for (int i = 1; i < rule.getLength(); i++) {
                int j = ruleTable[i - 1];
                while (j > 0 && rule.getByte(i) != rule.getByte(j)) {
                    j = ruleTable[j - 1];
                }
                if (rule.getByte(i) == rule.getByte(j)) {
                    j++;
                }
                ruleTable[i] = j;
            }
            table.put(rule, ruleTable);
        }
        return table;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;
        this.table = precompute(this.rules);
    }

    @Override
    public void search(Input input, Results results) {
        Result result = new Result(this.rules, input, this);
        result.start();

        for (Rule rule :
                this.rules) {
            Integer j = 0;
            for (int i = 0; i < input.getLength(); i++) {
                while (j > 0 && input.getByte(i) != rule.getByte(j)) {
                    j = this.table.get(rule)[j - 1];
                }
                if (input.getByte(i) == rule.getByte(j)) {
                    j++;
                    if (j == rule.getLength()) {
                        result.addLocation(i - j + 1);
                        j = 0;
                    }
                }
            }
        }

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Knuth-Morris-Pratt";
    }
}
