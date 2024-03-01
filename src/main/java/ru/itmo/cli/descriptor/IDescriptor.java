package ru.itmo.cli.descriptor;

/**
 * The IDescriptor interface represents a contract for classes that provide methods
 * to write and read data.
 */
public interface IDescriptor {

    /**
     * Writes the provided data.
     *
     * @param data The data to be written.
     */
    void write(String data);

    /**
     * Reads data from the descriptor.
     *
     * @return The data read from the descriptor.
     */
    String read();
}