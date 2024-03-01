package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.EchoCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest {
    @Test
    void echoTest() {
        //Arrange
        AppState state = new AppState();
        String args1[] = {"echo"};
        String args2[] = {"echo", "hello"};
        String args3[] = {"echo", "hi", "hello", "1", "2"};

        EchoCommand[] commands = {
                new EchoCommand(args1, state),
                new EchoCommand(args2, state),
                new EchoCommand(args3, state)
        };

        for (BaseCommand command: commands) {
            command.changeInOut(new DefaultOutDescriptor(), 1);
            command.changeInOut(new DefaultOutDescriptor(), 2);
        }

        //Act
        commands[0].execute();
        commands[1].execute();
        commands[2].execute();

        //Assert
        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, commands[0].getCommandData().getStatus()),
                () -> assertEquals("", commands[0].getCommandData().getStdout().read())
        );
        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, commands[1].getCommandData().getStatus()),
                () -> assertEquals("hello", commands[1].getCommandData().getStdout().read())
        );
        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, commands[2].getCommandData().getStatus()),
                () -> assertEquals("hi hello 1 2", commands[2].getCommandData().getStdout().read())
        );
    }
}
