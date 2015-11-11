package casa.kieran.algorithm.trie;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.input.Input;
import casa.kieran.result.Result;
import casa.kieran.result.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

import java.util.ArrayList;

/**
 * Created by kieran on 2015/11/10.
 */
public class Trie implements Algorithm {

    private static Trie instance;

    private Rules rules;

    private TrieStructure trieStructure;

    /**
     * Return the singlton Trie
     *
     * @param rules
     * @return
     */
    public static Trie getInstance(Rules rules) {
        if (instance == null) {
            instance = new Trie();
            instance.trieStructure = new TrieStructure();
            instance.changeRules(rules);
            return instance;
        }
        instance.changeRules(rules);
        return instance;
    }

    private void changeRules(Rules rules) {
        this.rules = rules;
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
            ArrayList<Byte> bytesToSearch = new ArrayList<>();
            for (int j = 0; j < input.getLength() - i; j++) {
                bytesToSearch.add(j, input.getByte(i + j));
            }
            this.trieStructure.search(bytesToSearch);
            bytesToSearch.clear();
        }
        result.end();
        results.addResult(result);
    }
}
