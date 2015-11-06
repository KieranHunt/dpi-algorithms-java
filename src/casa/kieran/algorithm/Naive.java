package casa.kieran.algorithm;

import casa.kieran.input.Input;
import casa.kieran.output.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

/**
 * Created by kieran on 2015/11/06.
 */
public class Naive implements Algorithm {
    private Rules rules;

    public Naive(Rules rules) {
        this.rules = rules;
    }

    @Override
    public void addRules(Rules rules) {
        this.rules = rules;
    }

    @Override
    public Results search(Input input) {
        Results results = new Results(this.rules);
        for (Rule rule :
                this.rules) {
            for (int i = 0; i < input.getLength() - rule.getRule().length(); i++) {
                int j = 0;
                while (j < rule.getRule().length() && input.getInputAt(i + j) == rule.getRule().charAt(j)) {
                    j++;
                }
                if (j == rule.getRule().length()) {
                    results.add(rule, i);
                }
            }
        }
        return results;
    }
}
