package casa.kieran.dpi.statistics.writer;

import casa.kieran.dpi.statistics.OverallStatistics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class StatisticsWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsWriter.class);

    private static final String DEFAULT_WRITE_LOCATION = "statistics.json";

    public static void writeStatistics(OverallStatistics overallStatistics) {

        LOGGER.warn("Using default write location: " + DEFAULT_WRITE_LOCATION);

        writeStatistics(overallStatistics, DEFAULT_WRITE_LOCATION);
    }

    private static void writeStatistics(OverallStatistics overallStatistics, String writeLocation) {
        LOGGER.info("Attempting to write out statistics");

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(writeLocation), overallStatistics);
            LOGGER.info("Successfully wrote statistics to " + writeLocation);
        } catch (IOException e) {
            LOGGER.error("Could not write out statistics");
            e.printStackTrace();
        }
    }
}
