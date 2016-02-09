package casa.kieran.dpi.input;

/**
 * Created by kieran on 2015/11/10.
 */
public interface Input {

    /**
     * Returns the byte at the given location.
     *
     * @param location The location to find the byte at.
     * @return The byte at the given location.
     */
    Byte getByte(Integer location);

    /**
     * Returns the length of the input
     *
     * @return the length of the input.
     */
    Integer getLength();

    /**
     * Returns the location of the original input file
     *
     * @return a string representing the location of the input
     */
    String getLocation();
}
