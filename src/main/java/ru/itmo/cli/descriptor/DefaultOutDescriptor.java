package ru.itmo.cli.descriptor;
/**
 * The DefaultOutDescriptor class implements the IDescriptor interface to provide functionality
 * for writing data to a StringBuilder buffer and reading from it.
 */
public class DefaultOutDescriptor implements IDescriptor {
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Writes the provided data to the internal buffer.
     *
     * @param data The data to be written.
     */
    @Override
    public void write(String data) {
        buffer.append(data);
    }

    /**
     * Reads data from the internal buffer.
     *
     * @return The data read from the internal buffer.
     */
    @Override
    public String read() {
        String result = buffer.toString();
        buffer.setLength(0);
        return result;
    }
}
