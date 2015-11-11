package casa.kieran.algorithm;

import casa.kieran.input.Input;
import casa.kieran.result.Result;
import casa.kieran.result.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

/**
 * Created by kieran on 2015/11/06.
 */
public class Naive implements Algorithm {

    private static Naive instance;

    private Rules rules;

    private Naive(Rules rules) {
        this.rules = rules;
    }

    public static Naive getInstance(Rules rules) {
        if (instance == null) {
            return new Naive(rules);
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;
    }

    @Override
    public void search(Input input, Results results) {
        Result result = new Result(this.rules, input, this);
        result.start();

        for (Rule rule :
                this.rules) {
            for (int i = 0; i < input.getLength() - rule.getLength(); i++) {
                int j = 0;
                while (j < rule.getLength() && input.getByte(i + j) == rule.getByte(j)) {
                    j++;
                }
                if (j == rule.getLength()) {
                    result.addLocation(i);
                }
            }
        }

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Naïve";
    }
}
