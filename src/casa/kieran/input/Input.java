package casa.kieran.input;

/**
 * Created by kieran on 2015/11/05.
 */
public interface Input {

    public Character getInputAt(Integer location);

    public Integer getLength();

    public Integer indexOf(Character character);

    public Integer indexOf(String string);

    public Integer indexOf(String string, Integer start);

}
