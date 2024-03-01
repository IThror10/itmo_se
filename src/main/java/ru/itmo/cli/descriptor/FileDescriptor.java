package ru.itmo.cli.descriptor;

import java.io.*;


/**
 * The FileDescriptor class implements the IDescriptor interface to provide functionality
 * for writing and reading data to/from a file.
 */
public class FileDescriptor implements IDescriptor {
    /**
     * Constructs a FileDescriptor object with the specified file name and path.
     *
     * @param fileName The name of the file.
     * @param path     The path of the file.
     */
    public FileDescriptor(String fileName, String path) {
        fileName = fileName.startsWith("/") || path == null ? fileName : path + "/" + fileName;
        this.fileName = fileName;
        File file = new File(fileName);
        found = file.exists();
    }

    /**
     * Writes the provided data to the file.
     *
     * @param data The data to be written.
     */
    @Override
    public void write(String data) {
        try {
            writer = new FileWriter(fileName, writer != null);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            // Ignore IOException
        }
    }

    /**
     * Reads data from the file.
     *
     * @return The data read from the file.
     */
    @Override
    public String read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            return content.toString();
        } catch (IOException e) {
            // Ignore IOException
        }
        return null;
    }

    /**
     * Indicates whether the file was found.
     *
     * @return true if the file was found, otherwise false.
     */
    public boolean found() {
        return found;
    }

    /**
     * Gets the file name.
     *
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
    }

    private final String fileName;
    private boolean found;
    private FileWriter writer = null;
}

