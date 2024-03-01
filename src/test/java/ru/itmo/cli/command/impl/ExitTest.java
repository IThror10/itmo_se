package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.ExitCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.*;

public class ExitTest {
    @Test
    void exitTest() {
        //Arrange
        AppState state = new AppState();
        String args1[] = {"exit"};
        String args2[] = {"exit", "1"};
        String args3[] = {"exit", "hello"};
        String args4[] = {"exit", "hi", "hello", "1", "2"};

        ExitCommand[] commands = {
                new ExitCommand(args1, state),
                new ExitCommand(args2, state),
                new ExitCommand(args3, state),
                new ExitCommand(args4, state)
        };

        for (BaseCommand command: commands) {
            command.changeInOut(new DefaultOutDescriptor(), 1);
            command.changeInOut(new DefaultOutDescriptor(), 2);
        }

        //Act
        commands[0].execute();
        commands[1].execute();
        commands[2].execute();
        commands[3].execute();

        //Assert
        assertAll(
                () -> assertEquals(CommandStatus.EXIT, commands[0].getCommandData().getStatus()),
                () -> assertEquals("exit", commands[0].getCommandData().getStdout().read()),
                () -> assertEquals("", commands[0].getCommandData().getStderr().read())
        );
        assertAll(
                () -> assertEquals(CommandStatus.EXIT, commands[1].getCommandData().getStatus()),
                () -> assertEquals("exit", commands[1].getCommandData().getStdout().read()),
                () -> assertEquals("", commands[1].getCommandData().getStderr().read())
        );
        assertAll(
                () -> assertEquals(CommandStatus.EXIT, commands[2].getCommandData().getStatus()),
                () -> assertEquals("exit", commands[2].getCommandData().getStdout().read()),
                () -> assertEquals("exit: hello: numeric argument required", commands[2].getCommandData().getStderr().read())
        );
        assertAll(
                () -> assertEquals(CommandStatus.ERROR, commands[3].getCommandData().getStatus()),
                () -> assertEquals("", commands[3].getCommandData().getStdout().read()),
                () -> assertEquals("exit: too many arguments", commands[3].getCommandData().getStderr().read())
        );
    }
}
