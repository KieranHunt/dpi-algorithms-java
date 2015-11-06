package casa.kieran;

import casa.kieran.algorithm.Algorithm;
import casa.kieran.algorithm.BuiltIn;
import casa.kieran.algorithm.KnuthMorrisPratt;
import casa.kieran.algorithm.Naive;
import casa.kieran.input.Input;
import casa.kieran.input.Packet;
import casa.kieran.input.Text;
import casa.kieran.rule.Rule;
import casa.kieran.rule.Rules;

import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        Packet packet = new Packet("tcpdump.pcap");

        String text = "Hello there you crazy person";

        Input input = new Text(text);

        Rule term = new Rule("crazy");
        Rule badRule = new Rule("asfsdf");
        Rule lots = new Rule("e");

        Rules rules = new Rules();
        rules.addRule(badRule);
        rules.addRule(term);
        rules.addRule(lots);

        ArrayList<Algorithm> algorithms = new ArrayList<>();
        algorithms.add(new BuiltIn(rules));
        algorithms.add(new Naive(rules));
        algorithms.add(new KnuthMorrisPratt(rules));

        for (Algorithm algorithm :
                algorithms) {
            System.out.println(algorithm.search(input));
        }


    }
}
