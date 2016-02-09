package casa.kieran.dpi.result;

import casa.kieran.dpi.test.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class Results implements Iterable<Result> {
    private Set<Result> results;
    private Test test;

    public Results(Test test) {
        this.results = new HashSet<>();
        this.test = test;
    }

    public void addResult(Result result) {
        this.results.add(result);
    }

    public Test getTest() {
        return test;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    @Override
    public Iterator<Result> iterator() {
        return this.results.iterator();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();

        this.results.forEach(stringBuffer::append);

        stringBuffer.append("Number of results: " + results.size());

        return stringBuffer.toString();
    }
}
