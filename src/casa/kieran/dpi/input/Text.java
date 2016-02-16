package casa.kieran.dpi.input;

public class Text implements Input {

    private String text;

    private String id;

    public Text(String input) {
        this.text = input;
        this.id = Integer.toString(text.hashCode());
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
    public String getID() {
        return id;
    }

    @Override
    public Integer getLength() {
        return this.text.length();
    }

    @Override
    public String toString() {
        return this.text;
    }

    @Override
    public String getLocation() {
        return this.toString();
    }

    @Override
    public String getString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Text text1 = (Text) o;

        return text.equals(text1.text);

    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
