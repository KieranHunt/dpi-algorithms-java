package casa.kieran.input;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inputs implements Iterable<Input> {

    List<Input> inputs;

    public Inputs() {
        inputs = new ArrayList<>();
    }

    @Override
    public Iterator<Input> iterator() {
        return inputs.iterator();
    }
}
