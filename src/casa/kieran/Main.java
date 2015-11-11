package casa.kieran;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.algorithm.Algorithms;
import casa.kieran.algorithm.KnuthMorrisPratt;
import casa.kieran.algorithm.Naive;
import casa.kieran.algorithm.trie.Trie;
import casa.kieran.input.Input;
import casa.kieran.input.Text;
import casa.kieran.result.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

public class Main {


    public static void main(String[] args) {

        Input input = new Text("Hello there you crazy");

        Rule goodRule = new Rule("crazy");
        Rule badRule = new Rule("afdsfds");
        Rule crazyRule = new Rule("e");

        Rules rules = new Rules();
        rules.addRule(goodRule);
        rules.addRule(badRule);
        rules.addRule(crazyRule);

        Results results = new Results();

        Algorithms algorithms = new Algorithms();
        algorithms.addAlgorithm(Naive.getInstance(rules));
        algorithms.addAlgorithm(KnuthMorrisPratt.getInstance(rules));
        algorithms.addAlgorithm(Trie.getInstance(rules));

        for (Algorithm algorithm :
                algorithms) {
            algorithm.search(input, results);
        }

        System.out.println(results);

    }
}
