package casa.kieran.algorithm.trie;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.input.Input;
import casa.kieran.result.Result;
import casa.kieran.result.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

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
    public void search(Input input, Results results) {
        Result result = new Result(this.rules, input, this);
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
}
