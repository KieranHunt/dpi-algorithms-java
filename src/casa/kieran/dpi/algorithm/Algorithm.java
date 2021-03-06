package casa.kieran.dpi.algorithm;

import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Results;

public interface Algorithm {

    /**
     * Start the search with the specific algorithm
     *
     * @param input     input The text to be searched through
     * @param results   the results object that needs to be added to
     * @param runNumber the number of the run currently being done
     */
    void search(Input input, Results results, int runNumber, String runId);

}
