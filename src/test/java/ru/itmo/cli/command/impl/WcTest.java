package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.WcCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WcTest {
    private final String first = "src/test/resources/testingReadFile";
    private final String second = "src/test/resources/textFile";
    @Test
    public void wcExecuteTest() {
        //Arrange
        AppState state = new AppState();
        state.setPath(System.getProperty("user.dir"));

        String[] twoFilesArgs = {"wc", first, second};
        String[] notFoundArgs = {"wc", "notExistingFile"};
        String[] fromStdinArgs = {"wc"};

        WcCommand twoFilesTest = new WcCommand(twoFilesArgs, state);
        WcCommand notFoundTest = new WcCommand(notFoundArgs, state);
        WcCommand fromStdinTest = new WcCommand(fromStdinArgs, state);
        WcCommand commands[] = {twoFilesTest, notFoundTest, fromStdinTest};

        for (BaseCommand command: commands) {
            command.changeInOut(new DefaultOutDescriptor(), 1);
            command.changeInOut(new DefaultOutDescriptor(), 2);
        }
        fromStdinTest.changeInOut(new DefaultOutDescriptor(), 0);
        fromStdinTest.getCommandData().getStdin().write("First Line\nSecond line");

        //Act
        twoFilesTest.execute();
        notFoundTest.execute();
        fromStdinTest.execute();

        //Assert
        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, twoFilesTest.getCommandData().getStatus()),
                () -> assertEquals("2 6 38 src/test/resources/testingReadFile\n11 60 278 src/test/resources/textFile\n13 66 316 total\n", twoFilesTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.ERROR, notFoundTest.getCommandData().getStatus()),
                () -> assertEquals("wc : notExistingFile : No such file or directory\n", notFoundTest.getCommandData().getStderr().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, fromStdinTest.getCommandData().getStatus()),
                () -> assertEquals("2 4 22\n", fromStdinTest.getCommandData().getStdout().read())
        );
    }
}
