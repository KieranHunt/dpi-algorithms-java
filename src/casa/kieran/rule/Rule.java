package casa.kieran.rule;

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

    public Integer getLength() {
        return rule.length();
    }

    @Override
    public String toString() {
        return this.rule;
    }
}
