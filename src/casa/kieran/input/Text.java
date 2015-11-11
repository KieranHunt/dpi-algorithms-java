package casa.kieran.input;

/**
 * Created by kieran on 2015/11/10.
 */
public class Text implements Input {

    private String text;

    public Text(String input) {
        this.text = input;
    }

    @Override
    public Byte getByte(Integer location) {
        try {
            return this.text.getBytes()[location];
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Index " + location + " out of range.");
        }
    }

    @Override
    public Integer getLength() {
        return this.text.length();
    }

    @Override
    public String toString() {
        return this.text;
    }
}
