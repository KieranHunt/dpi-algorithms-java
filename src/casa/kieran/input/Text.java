package casa.kieran.input;

/**
 * Created by kieran on 2015/11/06.
 */
public class Text implements Input {

    String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public Character getInputAt(Integer location) {
        return this.text.charAt(location);
    }

    @Override
    public Integer getLength() {
        return this.text.length();
    }

    @Override
    public Integer indexOf(Character character) {
        return this.text.indexOf(character);
    }

    @Override
    public Integer indexOf(String string) {
        return this.text.indexOf(string);
    }

    @Override
    public Integer indexOf(String string, Integer start) {
        return this.text.indexOf(string, start);
    }
}
