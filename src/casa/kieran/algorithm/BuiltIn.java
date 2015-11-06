package casa.kieran.algorithm;

import casa.kieran.input.Input;
import casa.kieran.output.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

/**
 * Created by kieran on 2015/11/05.
 */
public class BuiltIn implements Algorithm {

    Rules rules;

    /**
     * Initialize the BuiltIn algorithm
     *
     * @param rules The Rules object containing the Rule(s) to be searched for.
     */
    public BuiltIn(Rules rules) {
        this.rules = rules;
    }

    @Override
    public void addRules(Rules rules) {
        this.rules = rules;
    }

    @Override
    public Results search(Input input) {
        Results results = new Results(this.rules);
        for (Rule rule : this.rules) {
            Integer index = input.indexOf(rule.getRule());
            while (index >= 0) {
                results.add(rule, index);
                index = input.indexOf(rule.getRule(), index + 1);
            }
        }
        return results;
    }
}
