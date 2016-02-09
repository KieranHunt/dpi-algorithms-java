package casa.kieran.dpi.statistics.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Filter.class);

    private String filterKey;
    private Object filterValue;
    private boolean include;

    public Filter(String filterKey, Object filterValue, boolean include) {
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.include = include;
    }

    public boolean matches(Object objectToFilter) {

        //Capitalize the first letter of the filter key so that it can be used to create a getter
        filterKey = filterKey.substring(0, 1).toUpperCase() + filterKey.substring(1);

        String methodName = "get" + filterKey;
        try {
            Class parameters[] = {};
            Method methodToCall = objectToFilter.getClass().getDeclaredMethod(methodName);

            try {
                if (methodToCall.invoke(objectToFilter, parameters).equals(filterValue) == include) {
                    return true;
                } else {
                    return false;
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                LOGGER.error("Method " + methodName + "() is either inaccessible or there was an error when invoking");
                return false;
            }

        } catch (NoSuchMethodException e) {
            LOGGER.error("Object to be filtered does not contain the method: " + methodName + "()");
            return false;
        }
    }

    public String getFilterKey() {
        return filterKey;
    }

    public Object getFilterValue() {
        return filterValue;
    }

    public boolean isInclude() {
        return include;
    }
}
