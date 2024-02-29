package ru.itmo.cli.descriptor;

public interface IDescriptor {
    void write(String output);
    String read();
}
