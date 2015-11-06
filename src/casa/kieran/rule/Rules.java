package casa.kieran.rule;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by kieran on 2015/11/05.
 */
public class Rules implements Iterable<Rule> {

    private ArrayList<Rule> rules;

    /**
     * Create a new Rules object containing many Rule objects
     */
    public Rules() {
        this.rules = new ArrayList<>();
    }

    /**
     * Add a new Rule object to the Rules object
     *
     * @param rule The new Rule object to be added.
     */
    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    /**
     * Clear all of the rules from the Rules object
     */
    public void clearRules() {
        this.rules.clear();
    }

    @Override
    public Iterator<Rule> iterator() {
        Iterator<Rule> ruleIterator = new Iterator<Rule>() {

            private Integer currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < rules.size();
            }

            @Override
            public Rule next() {
                return rules.get(currentIndex++);
            }
        };
        return ruleIterator;
    }
}
