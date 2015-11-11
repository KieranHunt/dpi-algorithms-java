package casa.kieran.algorithm;

import casa.kieran.input.Input;
import casa.kieran.result.Results;

/**
 * Created by kieran on 2015/11/05.
 */
public interface Algorithm {

    /**
     * Start the search with the specific algorithm
     *
     * @param input   input The text to be searched through
     * @param results the results object that needs to be added to
     */
    public void search(Input input, Results results);
}
