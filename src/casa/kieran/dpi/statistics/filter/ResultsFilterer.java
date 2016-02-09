package casa.kieran.dpi.statistics.filter;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ResultsFilterer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultsFilterer.class);

    public static List<Result> getFilteredResultList(Results results, String filterKey, Object filterValue, boolean include) {

        //Capitalize the first letter of the filter key so that it can be used to create a getter
        filterKey = filterKey.substring(0, 1).toUpperCase() + filterKey.substring(1);

        List<Result> resultList = new ArrayList<>();

        try {
            Class noparams[] = {};
            Method methodToCall = Result.class.getDeclaredMethod("get" + filterKey);

            results.forEach(result -> {
                try {
                    if (methodToCall.invoke(result, noparams).equals(filterValue) == include) {
                        resultList.add(result);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        } catch (NoSuchMethodException e) {
            LOGGER.error("Filter Key i.e. field name does not exist", e);
            e.printStackTrace();
        }

        return resultList;
    }
}
