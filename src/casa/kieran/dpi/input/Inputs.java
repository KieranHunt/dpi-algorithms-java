package casa.kieran.dpi.input;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inputs implements Iterable<Input> {

    private List<Input> inputs;

    public Inputs() {
        inputs = new ArrayList<>();
    }

    public void addInput(Input input) {
        inputs.add(input);
    }

    public void addInputList(List<Input> inputList) {
        inputs.addAll(inputList);
    }

    public void addInputs(Inputs inputs) {
        inputs.forEach(input -> this.inputs.add(input));
    }

    public int getNumberOfInputs() {
        return inputs.size();
    }

    @Override
    public Iterator<Input> iterator() {
        return inputs.iterator();
    }
}
