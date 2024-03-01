package ru.itmo.cli.descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The DefaultInDescriptor class implements the IDescriptor interface to provide functionality
 * for reading data from the standard input stream (System.in).
 */
public class DefaultInDescriptor implements IDescriptor {

    /**
     * Writes data to the standard input stream (System.in).
     * Does nothing as writing to System.in is not supported
     *
     * @param data The data to be written.
     */
    @Override
    public void write(String data) {
        // This method intentionally left blank as writing to System.in is not supported.
    }

    /**
     * Reads data from the standard input stream (System.in).
     *
     * @return The data read from the standard input stream.
     */
    @Override
    public String read() {
        StringBuilder input = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;

            while ((line = reader.readLine()) != null) {
                input.append(line).append("\n");
            }
        } catch (IOException e) {
            // Handle IOException
        }
        return input.toString();
    }
}
