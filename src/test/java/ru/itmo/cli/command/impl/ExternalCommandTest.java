package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.ExternalCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExternalCommandTest {
    public void externalExecuteTest() {
        //Arrange
        String[] echoArgs = {"echo", "data", "res"};
        String[] echoInArgs = {"echo"};
        String[] errorArgs = {"cat", "SomeFileThatIsNotExistingForSure.abc"};
        String[] noExternalArgs = {"NowTheCommandDoesNotExist.abc"};

        AppState state = Mockito.mock(AppState.class);
        ExternalCommand echoTest = new ExternalCommand(echoArgs, state);
        ExternalCommand echoInTest = new ExternalCommand(echoInArgs, state);
        ExternalCommand errorTest = new ExternalCommand(errorArgs, state);
        ExternalCommand noExternalTest = new ExternalCommand(noExternalArgs, state);
        ExternalCommand[] commands = {echoTest, echoInTest, errorTest, noExternalTest};

        for (BaseCommand command: commands) {
            command.changeInOut(new DefaultOutDescriptor(), 0);
            command.changeInOut(new DefaultOutDescriptor(), 1);
            command.changeInOut(new DefaultOutDescriptor(), 2);
        }
        echoInTest.getCommandData().getStdin().write("String to write");

        //Act
        echoTest.execute();
        echoInTest.execute();
        errorTest.execute();
        noExternalTest.execute();

        //Assert
        assertAll(
                () -> Assertions.assertEquals(CommandStatus.SUCCESS, echoTest.getCommandData().getStatus()),
                () -> assertEquals("echo data res", echoTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, echoInTest.getCommandData().getStatus()),
                () -> assertEquals("String to write", echoInTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.ERROR, errorTest.getCommandData().getStatus()),
                () -> assertEquals("cat: someFileThatIsNotExisting: No such file or directory", errorTest.getCommandData().getStderr().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.ERROR, errorTest.getCommandData().getStatus()),
                () -> assertEquals("cat: someFileThatIsNotExisting: No such file or directory", errorTest.getCommandData().getStderr().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.ERROR, noExternalTest.getCommandData().getStatus()),
                () -> assertEquals("bash: NowTheCommandDoesNotExist.abc: command not found", noExternalTest.getCommandData().getStderr().read())
        );
    }
}
