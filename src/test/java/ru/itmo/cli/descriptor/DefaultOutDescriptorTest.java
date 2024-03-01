package ru.itmo.cli.descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultOutDescriptorTest {
    @Test
    void defaultOutReadWriteTest() {
        //Arrange
        DefaultOutDescriptor singleWrite = new DefaultOutDescriptor();
        DefaultOutDescriptor doubleWrite = new DefaultOutDescriptor();

        //Act
        singleWrite.write("String1");
        doubleWrite.write("Str1");
        doubleWrite.write("Str2");

        //Assert
        assertEquals("String1", singleWrite.read());
        assertEquals("Str1Str2", doubleWrite.read());
    }
}
