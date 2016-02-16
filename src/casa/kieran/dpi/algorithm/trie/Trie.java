package casa.kieran.dpi.algorithm.trie;

import casa.kieran.dpi.algorithm.AbstractAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kieran on 2015/11/10.
 */
public class Trie extends AbstractAlgorithm implements Algorithm {

    private static Trie instance;

    private Rules rules;

    private TrieStructure trieStructure;

    /**
     * Return the singleton Trie
     *
     * @param rules
     * @return
     */
    public static Trie getInstance(Rules rules) {
        if (instance == null) {
            instance = new Trie();
            instance.changeRules(rules);
            return instance;
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;
        this.trieStructure = new TrieStructure();
        for (Rule rule :
                rules) {
            ArrayList<Byte> bytes = new ArrayList<>();
            for (int i = 0; i < rule.getLength(); i++) {
                bytes.add(i, rule.getByte(i));
            }
            this.trieStructure.insert(bytes);
        }
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(this.rules, input, this, runNumber, runId);
        result.start();

        Runnable runnable = new TrieRunnable(input, result);
        List<Runnable> runnables = Arrays.asList(runnable);
        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Trie";
    }

    private class TrieRunnable implements Runnable {
        private Input input;
        private Result result;

        public TrieRunnable(Input input, Result result) {
            this.input = input;
            this.result = result;
        }

        public void run() {
            for (int i = 0; i < input.getLength(); i++) {
                List<Byte> searchList = getSublist(input, i, input.getLength());
                if (trieStructure.search(searchList)) {
                    result.addLocation(i);
                }
            }
        }

        private ArrayList<Byte> getSublist(Input input, Integer start, Integer end) {
            ArrayList<Byte> bytes = new ArrayList<>();
            for (int i = start; i < end; i++) {
                bytes.add(input.getByte(i));
            }
            return bytes;
        }
    }
}
