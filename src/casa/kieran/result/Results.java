package casa.kieran.result;

import java.util.HashSet;
import java.util.Iterator;


public class Results implements Iterable<Result> {
    private HashSet<Result> results;

    public Results() {
        this.results = new HashSet<>();
    }

    public void addResult(Result result) {
        this.results.add(result);
    }

    @Override
    public Iterator<Result> iterator() {
        return this.results.iterator();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        this.results.forEach(stringBuffer::append);

        return stringBuffer.toString();
    }
}
