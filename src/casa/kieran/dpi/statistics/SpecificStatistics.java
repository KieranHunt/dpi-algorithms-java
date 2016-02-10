package casa.kieran.dpi.statistics;

import casa.kieran.dpi.result.Results;

import java.util.Map;

public interface SpecificStatistics {

    /**
     * Generate a statistics map.
     * <p>
     * This is designed so that implementors can generate a variable to statistics mapping ont their own.
     *
     * @param results - An object containing all of the results
     * @return a map of some key to a Statistics Object
     */
    Map<String, Statistics> generateStatisticsMap(Results results);
}
