package casa.kieran.dpi.rule;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Rule {
    private String rule;

    public Rule(String rule) {
        this.rule = rule;
    }

    public Byte getByte(Integer location) {
        try {
            return rule.getBytes()[location];
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Index " + location + " out of range.");
        }
    }

    @JsonIgnore
    public Integer getLength() {
        return rule.length();
    }

    public String getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return this.rule;
    }
}
