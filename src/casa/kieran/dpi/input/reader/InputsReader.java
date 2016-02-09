package casa.kieran.dpi.input.reader;

import casa.kieran.dpi.input.Input;
import casa.kieran.dpi.input.Inputs;
import casa.kieran.dpi.input.Pcap;
import casa.kieran.dpi.input.TextFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputsReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputsReader.class);

    private static final String DEFAULT_LOCATION = "resources/inputs.txt";

    public static Inputs readInputsFile() {
        return readInputsFile(DEFAULT_LOCATION);
    }

    public static Inputs readInputsFile(String location) {

        LOGGER.info("Reading Inputs from " + location);

        List<String> listOfLines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(location));
            while (scanner.hasNext()) {
                listOfLines.add(scanner.next());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Inputs inputs = new Inputs();

        for (String line : listOfLines) {
            List<String> splitLine = Arrays.asList(line.split(","));

            switch (splitLine.get(0)) {
                case "text":
                    LOGGER.info("Reading text from " + splitLine.get(1));
                    Input text = new TextFile(splitLine.get(1));
                    inputs.addInput(text);
                    break;
                case "pcap":
                    LOGGER.info("Reading pcap from " + splitLine.get(1));
                    List<Input> pcap = (new Pcap(splitLine.get(1))).getAllPackets();
                    inputs.addInputList(pcap);
                    break;
            }
        }
        return inputs;
    }
}
