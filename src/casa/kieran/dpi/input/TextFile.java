package casa.kieran.dpi.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;

public class TextFile implements Input {

    private static Logger LOGGER = LoggerFactory.getLogger(TextFile.class);

    private String text;

    private String location;

    public TextFile(final String location) {
        try {
            text = new String(readAllBytes(get(location)));
            this.location = location;

            LOGGER.info("Read " + text.length() + " characters from " + location);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Byte getByte(Integer location) {
        return text.getBytes()[location];
    }

    @Override
    public Integer getLength() {
        return text.length();
    }

    @Override
    public String toString() {
        return location;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextFile textFile = (TextFile) o;

        return location.equals(textFile.location);

    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
