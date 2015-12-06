package casa.kieran.rule;

import java.util.HashSet;
import java.util.Iterator;

public class Rules implements Iterable<Rule> {
    private HashSet<Rule> rules;

    public Rules() {
        this.rules = new HashSet<>();
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    @Override
    public Iterator<Rule> iterator() {
        return this.rules.iterator();
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        this.forEach((rule) -> stringBuffer.append(rule + " "));
        return stringBuffer.toString();
    }
}
