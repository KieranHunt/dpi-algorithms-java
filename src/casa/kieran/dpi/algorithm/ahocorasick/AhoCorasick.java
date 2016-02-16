package casa.kieran.dpi.algorithm.ahocorasick;


import casa.kieran.dpi.algorithm.AbstractParallelizableAlgorithm;
import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rules;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AhoCorasick extends AbstractParallelizableAlgorithm implements Algorithm {

    private static AhoCorasick instance;

    private Rules rules = new Rules();

    private Trie trie;

    public static AhoCorasick getInstance(Rules rules) {
        if (instance == null) {
            instance = new AhoCorasick();
        }
        if (!rules.equals(instance.rules)) {
            changeRules(instance, rules);
        }

        return instance;
    }

    private static void changeRules(AhoCorasick ahoCorasick, Rules rules) {
        ahoCorasick.rules = rules;

        Trie.TrieBuilder trieBuilder = Trie.builder();

        rules.forEach(rule -> {
            trieBuilder.addKeyword(rule.getRule());
        });

        ahoCorasick.trie = trieBuilder.build();
    }

    @Override
    public void search(Input input, Results results, int runNumber, String runId) {
        Result result = new Result(rules, input, this, runNumber, runId);
        result.start();

        Runnable runnable = new AhoCorasickRunnable(input, result);
        List<Runnable> runnables = Arrays.asList(runnable);
        executeSearch(runnables);

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Aho-Corasick";
    }

    private class AhoCorasickRunnable implements Runnable {
        private Input input;
        private Result result;

        public AhoCorasickRunnable(Input input, Result result) {
            this.input = input;
            this.result = result;
        }

        public void run() {
            Collection<Emit> emits = trie.parseText(input.getString());
            emits.forEach(emit -> {
                result.addLocation(emit.getStart());
            });
        }
    }
}

