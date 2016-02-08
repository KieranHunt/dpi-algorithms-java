package casa.kieran;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.algorithm.Algorithms;
import casa.kieran.algorithm.bitap.Bitap;
import casa.kieran.algorithm.boyermoore.BoyerMoore;
import casa.kieran.algorithm.knuthmorrispratt.KnuthMorrisPratt;
import casa.kieran.algorithm.naive.Naive;
import casa.kieran.algorithm.trie.Trie;
import casa.kieran.input.Input;
import casa.kieran.input.Packet;
import casa.kieran.input.Pcaps;
import casa.kieran.input.Text;
import casa.kieran.result.Results;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

public class Main {


    public static void main(String[] args) {

        Input input = new Text("Hello there you crazy");

        Pcaps pcaps = new Pcaps("tcpdump.pcap");
        Packet onePacket = pcaps.iterator().next();

        Rule goodRule = new Rule("crazy");
        Rule badRule = new Rule("Hello there");
        Rule crazyRule = new Rule("e");
        Rule isThere = new Rule("google");

        Rules rules = new Rules();
        rules.addRule(goodRule);
        rules.addRule(badRule);
        rules.addRule(crazyRule);
        rules.addRule(isThere);

        Results results = new Results();

        Algorithms algorithms = new Algorithms();
        algorithms.addAlgorithm(Naive.getInstance(rules));
        algorithms.addAlgorithm(KnuthMorrisPratt.getInstance(rules));
        algorithms.addAlgorithm(Trie.getInstance(rules));
        algorithms.addAlgorithm(Bitap.getInstance(rules));
        algorithms.addAlgorithm(BoyerMoore.getInstance(rules));

        for (Algorithm algorithm :
                algorithms) {
            algorithm.search(input, results);
            algorithm.search(onePacket, results);
        }

        System.out.println(results);

    }
}
