package casa.kieran.dpi.algorithm.trie;

import casa.kieran.dpi.algorithm.Algorithm;
import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.rule.Rule;
import casa.kieran.dpi.rule.Rules;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kieran on 2015/11/10.
 */
public class Trie implements Algorithm {

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
        for (int i = 0; i < input.getLength(); i++) {
            List<Byte> searchList = getSublist(input, i, input.getLength());
            if (this.trieStructure.search(searchList)) {
                result.addLocation(i);
            }
        }
        result.end();
        results.addResult(result);
    }

    private ArrayList<Byte> getSublist(Input input, Integer start, Integer end) {
        ArrayList<Byte> bytes = new ArrayList<>();
        for (int i = start; i < end; i++) {
            bytes.add(input.getByte(i));
        }
        return bytes;
    }

    @Override
    public String toString() {
        return "Trie";
    }
}
