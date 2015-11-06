package casa.kieran.output;

import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

import java.util.HashMap;

/**
 * Created by kieran on 2015/11/05.
 * This class represents a result from a searching algorithm. It can handle searching for multiple rules and records
 * each Rule's result in the Result class.
 */
public class Results {

    HashMap<Rule, Result> resultHashMap;

    /**
     * Create an instance of the Result object.
     *
     * @param rules The list of rules being searched for.
     */
    public Results(Rules rules) {
        this.resultHashMap = new HashMap<>();
        for (Rule rule : rules) {
            resultHashMap.put(rule, new Result());
        }
    }

    /**
     * add a result when a search algorithm matches a rule to a piece of text.
     *
     * @param rule     The rule that has been matched
     * @param position The position that a rule has been found in the text.
     */
    public void add(Rule rule, Integer position) {
        Result result = this.resultHashMap.get(rule);
        try {
            result.add(position);
        } catch (NullPointerException e) {
            throw e;
        }
    }

    /**
     * Clear the results.
     */
    public void clear() {
        resultHashMap.clear();
    }

    @Override
    public String toString() {

        StringBuffer stringBuffer = new StringBuffer();

        for (HashMap.Entry entry :
                resultHashMap.entrySet()) {
            stringBuffer.append("[ " + ((Rule) entry.getKey()) + " ] " + ((Result) entry.getValue()) + "\n");
        }

        return stringBuffer.toString();
    }
}
