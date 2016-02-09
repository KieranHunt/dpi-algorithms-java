package casa.kieran.dpi.algorithm;

import java.util.HashSet;
import java.util.Iterator;

public class Algorithms implements Iterable<Algorithm> {
    private HashSet<Algorithm> algorithms;

    public Algorithms() {
        this.algorithms = new HashSet<>();
    }

    public void addAlgorithm(Algorithm algorithm) {
        this.algorithms.add(algorithm);
    }

    public int getNumberOfAlgorithms() {
        return algorithms.size();
    }

    @Override
    public Iterator<Algorithm> iterator() {
        return this.algorithms.iterator();
    }
}
