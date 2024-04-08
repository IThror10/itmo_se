package ru.itmo.cli.descriptor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileDescriptorTest {
    @Test
    void fileDescriptorReadTest() {
        //Arrange
        FileDescriptor readDesc = new FileDescriptor("src/test/resources/testingReadFile", System.getProperty("user.dir"));

        //Act
        String result = readDesc.read();

        //Assert
        assertEquals("Some data to read\nImportant passwords\n", result);
    }

    @Test
    void fileDescriptorReadWriteTest() {
        //Arrange
        FileDescriptor writeDesc = new FileDescriptor("src/test/resources/testingWriteFile", System.getProperty("user.dir"));
        FileDescriptor writeTwice = new FileDescriptor("src/test/resources/testingWriteFile", System.getProperty("user.dir"));

        //Act
        writeDesc.write("First string");
        String result1 = writeDesc.read();
        writeTwice.write("First string");
        writeTwice.write("Second string");
        String result2 = writeTwice.read();

        //Assert
        assertEquals("First string\n", result1);
        assertEquals("First stringSecond string\n", result2);
    }
}
