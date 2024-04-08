package ru.itmo.cli.descriptor;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultInDescriptorTest {
    @Test
    void defaultInReadWriteTest() {
        //Arrange
        InputStream sysInBackup = System.in;
        ByteArrayInputStream noWriteIn = new ByteArrayInputStream("Some string to read".getBytes());
        ByteArrayInputStream writeBeforeIgnoreIn = new ByteArrayInputStream("Other string to read".getBytes());
        ByteArrayInputStream writeAfterIgnoreIn = new ByteArrayInputStream("Last string to read".getBytes());

        DefaultInDescriptor noWriteDesc = new DefaultInDescriptor();
        DefaultInDescriptor writeBeforeIgnoreDesc = new DefaultInDescriptor();
        DefaultInDescriptor writeAfterIgnoreDesc = new DefaultInDescriptor();

        //Act
        System.setIn(noWriteIn);
        String noWriteResult = noWriteDesc.read();

        System.setIn(writeBeforeIgnoreIn);
        writeBeforeIgnoreDesc.write("Rewrite");
        String writeBeforeResult = writeBeforeIgnoreDesc.read();

        System.setIn(writeAfterIgnoreIn);
        String writeAfterResult = writeAfterIgnoreDesc.read();
        writeAfterIgnoreDesc.write("Rewrite");
        String empty = writeAfterIgnoreDesc.read();

        System.setIn(sysInBackup);

        //Assert
        assertEquals("Some string to read", noWriteResult);
        assertEquals("Other string to read", writeBeforeResult);
        assertEquals("Last string to read", writeAfterResult);
        assertEquals("", empty);
    }
}
