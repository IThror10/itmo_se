package ru.itmo.cli.descriptor;

public interface IDescriptor {
    void write(String data);
    String read();
}