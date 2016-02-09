package casa.kieran.dpi.input;

import casa.kieran.dpi.exception.UnimplementedInputTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputsFactory {

    private static Logger LOGGER = LoggerFactory.getLogger(InputsFactory.class);

    public static Inputs getInputs(String inputType, String inputLocation) throws UnimplementedInputTypeException {
        Inputs inputs = new Inputs();

        switch (inputType) {
            case "pcap":
                LOGGER.info("Found pcap entry at " + inputLocation);
                Pcap pcap = new Pcap(inputLocation);
                inputs.addInputList(pcap.getAllPackets());
                break;
            case "text":
                LOGGER.info("Found text entry at" + inputLocation);
                TextFile textFile = new TextFile(inputLocation);
                inputs.addInput(textFile);
                break;
            default:
                LOGGER.error("Unimplemented Input type: " + inputType, new UnimplementedInputTypeException());
                throw new UnimplementedInputTypeException();
        }

        return inputs;
    }
}
