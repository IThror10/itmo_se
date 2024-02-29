package ru.itmo.cli.descriptor;

public class FileDescriptor implements IDescriptor {
    private String filename;

    public FileDescriptor(String fileName) {

    }

    @Override
    public void write(String output) {

    }

    @Override
    public String read() {
        return null;
    }

    public boolean found() {
        return true;
    }

    public String getFileName() { return filename;}
}
