package casa.kieran.algorithm;

import casa.kieran.input.Input;
import casa.kieran.output.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

import java.util.HashMap;

/**
 * Created by kieran on 2015/11/06.
 */
public class KnuthMorrisPratt implements Algorithm {

    private Rules rules;

    private HashMap<Rule, Integer[]> table;

    public KnuthMorrisPratt(Rules rules) {
        this.rules = rules;
        this.table = precompute(this.rules);
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
            Integer[] ruleTable = new Integer[rule.getRule().length()];
            ruleTable[0] = 0;
            for (int i = 1; i < rule.getRule().length(); i++) {
                int j = ruleTable[i - 1];
                while (j > 0 && rule.getRule().charAt(i) != rule.getRule().charAt(j)) {
                    j = ruleTable[j - 1];
                }
                if (rule.getRule().charAt(i) == rule.getRule().charAt(j)) {
                    j++;
                }
                ruleTable[i] = j;
            }
            table.put(rule, ruleTable);
        }
        return table;
    }

    @Override
    public void addRules(Rules rules) {
        this.rules = rules;
        this.table = precompute(this.rules);
    }

    @Override
    public Results search(Input input) {
        Results results = new Results(this.rules);
        for (Rule rule :
                this.rules) {
            Integer j = 0;
            for (int i = 0; i < input.getLength(); i++) {
                while (j > 0 && input.getInputAt(i) != rule.getRule().charAt(j)) {
                    j = this.table.get(rule)[j - 1];
                }
                if (input.getInputAt(i) == rule.getRule().charAt(j)) {
                    j++;
                    if (j == rule.getRule().length()) {
                        results.add(rule, i - j + 1);
                        j = 0;
                    }
                }
            }
        }
        return results;
    }
}
