package casa.kieran.dpi.result.printable;

import casa.kieran.dpi.result.Result;
import casa.kieran.dpi.result.Results;
import casa.kieran.dpi.test.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PrintableResultsFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrintableResultsFactory.class);

    public static void printResults(String location, Test test) {
        List<PrintableResult> printableResultList = new ArrayList<>();

        Results results = test.getResults();

        for (Result result : results) {
            long start = result.getStart();
            long end = result.getEnd();
            long elapsed = result.getElapsed();
            List<String> rules = new ArrayList<>();
            result.getRules().forEach(rule -> {
                rules.add(rule.toString());
            });
            Set<Integer> locations = result.getLocations();
            String algorithm = result.getAlgorithmClass().getSimpleName();
            String inputFile = result.getInputLocation();
            String inputID = result.getInput().getID();
            int runNumber = result.getRunNumber();
            String runId = result.getRunId();

            PrintableResult printableResult = new PrintableResult(
                    start, end, elapsed, rules, locations, algorithm, inputFile, inputID, runNumber, runId);
            printableResultList.add(printableResult);
        }

        LOGGER.info("Attempting to write out raw results");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(location), printableResultList);
            LOGGER.info("Successfully wrote raw results to " + location);
        } catch (IOException e) {
            LOGGER.error("Could not write out raw results");
            e.printStackTrace();
        }
    }
}
