package casa.kieran.algorithm;

import casa.kieran.input.Input;
import casa.kieran.output.Results;
import casa.kieran.rule.Rules;

/**
 * Created by kieran on 2015/11/05.
 */
public interface Algorithm {

    /**
     * Add the rules to the search algorithm
     *
     * @param rules The Rules object containing the rules to be searched for.
     */
    public void addRules(Rules rules);

    /**
     * Start the search with the specific algorithm
     *
     * @param input The text to be searched through
     * @return The Results object which encapsulates the results of a search.
     */
    public Results search(Input input);
}
