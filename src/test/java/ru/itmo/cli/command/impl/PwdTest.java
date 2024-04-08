package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.PwdCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest {
    @Test
    public void pwdExecuteTest() {
        //Arrange
        AppState state = Mockito.mock(AppState.class);
        Mockito.when(state.getPath()).thenReturn("cur/path");

        String call[] = {"pwd"};
        String wrong[] = {"pwd", "second"};

        PwdCommand callTest = new PwdCommand(call, state);
        PwdCommand wrongTest = new PwdCommand(wrong, state);

        callTest.changeInOut(new DefaultOutDescriptor(), 1);
        wrongTest.changeInOut(new DefaultOutDescriptor(), 2);

        //Act
        callTest.execute();
        wrongTest.execute();

        //Assert
        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, callTest.getCommandData().getStatus()),
                () -> assertEquals("cur/path", callTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.ERROR, wrongTest.getCommandData().getStatus()),
                () -> assertEquals("Wrong args amount for Pwd Command", wrongTest.getCommandData().getStderr().read())
        );
    }
}
