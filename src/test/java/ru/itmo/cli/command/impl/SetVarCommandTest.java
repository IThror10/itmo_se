package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.SetVarCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetVarCommandTest {
    @Test
    void setVarExecuteTest() {
        //Arrange
        AppState state = new AppState();
        String args1[] = {"val1=data text"};
        String args2[] = {"val2=\"Other = Operator\""};
        String args3[] = {"val3=\"val3\"", "Forever"};

        SetVarCommand commands[] = {
                new SetVarCommand(args1, state),
                new SetVarCommand(args2, state),
                new SetVarCommand(args3, state)
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
                () -> Assertions.assertEquals(CommandStatus.SUCCESS, commands[0].getCommandData().getStatus()),
                () -> assertEquals("data text", state.getVar("val1"))
        );
        assertAll(
                () -> assertEquals(CommandStatus.ERROR, commands[1].getCommandData().getStatus()),
                () -> assertEquals("Operator= must occur only once", commands[1].getCommandData().getStderr().read())
        );
        assertAll(
                () -> assertEquals(CommandStatus.ERROR, commands[2].getCommandData().getStatus()),
                () -> assertEquals("Wrong amount of arguments for .*=.* command", commands[2].getCommandData().getStderr().read())
        );
    }
}
