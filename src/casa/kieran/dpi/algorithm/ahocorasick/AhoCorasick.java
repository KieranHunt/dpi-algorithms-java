package casa.kieran.dpi.algorithm.ahocorasick;


import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rules;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;

import java.util.Collection;

public class AhoCorasick implements Algorithm {

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

        Collection<Emit> emits = this.trie.parseText(input.getString());
        emits.forEach(emit -> {
            result.addLocation(emit.getStart());
        });

        result.end();
        results.addResult(result);
    }

    @Override
    public String toString() {
        return "Aho-Corasick";
    }
}

