package casa.kieran.algorithm;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by kieran on 2015/11/10.
 */
public class Algorithms implements Iterable<Algorithm> {
    private HashSet<Algorithm> algorithms;

    public Algorithms() {
        this.algorithms = new HashSet<>();
    }

    public void addAlgorithm(Algorithm algorithm) {
        this.algorithms.add(algorithm);
    }

    @Override
    public Iterator<Algorithm> iterator() {
        return this.algorithms.iterator();
    }
}
