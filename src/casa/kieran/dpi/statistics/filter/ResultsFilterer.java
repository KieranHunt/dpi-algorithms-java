package casa.kieran.dpi.statistics.filter;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultsFilterer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultsFilterer.class);

    public static List<Result> getFilteredResultList(Results results, Filter filter) {
        return getFilteredResultList(results, Arrays.asList(filter));
    }

    public static List<Result> getFilteredResultList(Results results, List<Filter> filters) {

        List<Result> resultList = new ArrayList<>();

        for (Result result : results) {
            boolean canAdd = true;
            for (Filter filter : filters) {
                if (!filter.matches(result)) {
                    canAdd = false;
                    break;
                }
            }

            if (canAdd) {
                resultList.add(result);
            }
        }

        return resultList;
    }
}
