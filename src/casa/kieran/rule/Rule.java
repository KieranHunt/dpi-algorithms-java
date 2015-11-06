package casa.kieran.rule;

/**
 * Created by kieran on 2015/11/05.
 */
public class Rule {
    private String rule;

    /**
     * Create a new Rule object
     *
     * @param rule The text containing the new rule
     */
    public Rule(String rule) {
        this.rule = rule;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return this.rule;
    }
}
