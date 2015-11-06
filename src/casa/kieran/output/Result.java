package casa.kieran.output;

import java.util.ArrayList;

/**
 * Created by kieran on 2015/11/05.
 */
public class Result {
    private ArrayList<Integer> result;

    /**
     * Initialize a result object
     */
    public Result() {
        this.result = new ArrayList<>();
    }

    /**
     * Add a new position to the Result object
     *
     * @param position
     */
    public void add(Integer position) {
        this.result.add(position);
    }

    /**
     * Clear the Result object
     */
    public void clear() {
        result.clear();
    }

    @Override
    public String toString() {

        StringBuffer stringBuffer = new StringBuffer();

        for (Integer position :
                this.result) {
            stringBuffer.append(position + " ");
        }

        return stringBuffer.toString();
    }
}
