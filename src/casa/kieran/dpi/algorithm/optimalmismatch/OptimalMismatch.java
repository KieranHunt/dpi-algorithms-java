package casa.kieran.dpi.algorithm.optimalmismatch;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptimalMismatch extends AbstractAlgorithm {

    private static OptimalMismatch instance;

    private Rules rules;

    public static OptimalMismatch getInstance(Rules rules) {
        if (instance == null) {
            instance = new OptimalMismatch();
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {

        this.rules = rules;


    }

    private Map<Rule, List<Integer>> orderPattern(Rules rules) {
        Map<Rule, List<Integer>> preOrderPattern = new HashMap<>();

        rules.forEach(rule -> {
            int i;

            int m = rule.getLength();

            for (i = 0; i <= m; ++i) {

            }
        });

        return preOrderPattern;
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {

    }

    private class Pattern {
        int loc;
        char c;

        public int getLoc() {
            return loc;
        }

        public void setLoc(int loc) {
            this.loc = loc;
        }

        public char getC() {
            return c;
        }

        public void setC(char c) {
            this.c = c;
        }
    }
}
