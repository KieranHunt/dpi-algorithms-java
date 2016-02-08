package casa.kieran.algorithm.bitap;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.input.Input;
import casa.kieran.result.Result;
import casa.kieran.result.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

/**
 * Created by kieran on 2015/12/11.
 */
public class Bitap implements Algorithm {

    private static Bitap instance;

    private Rules rules;

    public static Bitap getInstance(Rules rules) {
        if (instance == null) {
            instance = new Bitap();
        }
        instance.rules = rules;
        return instance;
    }

    @Override
    public void search(Input input, Results results) {
        Result result = new Result(rules, input, this);
        result.start();

        Integer textLength = input.getLength();

        for (Rule rule :
                rules) {
            Integer ruleLength = rule.getLength();

            if (ruleLength == 0) {
                result.addLocation(0);
            }

            boolean[] r = new boolean[ruleLength + 1];
            r[0] = true;

            for (int i = 0; i < textLength; i++) {
                for (int j = ruleLength; j >= 1; j--) {
                    r[j] = r[j - 1] && (input.getByte(i) == rule.getByte(j - 1));
                }
                if (r[ruleLength]) {
                    result.addLocation(i - ruleLength + 1);
                }
            }
        }
        result.end();
        results.addResult(result);
    }
}
