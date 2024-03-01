package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.CatCommand;
import ru.itmo.cli.command.imp.WcCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatTest {
    private final String first = "src/test/resources/testingReadFile";
    private final String firstContent = "Some data to read\nImportant passwords\n";
    @Test
    public void catExecuteTest() {

        //Arrange
        AppState state = new AppState();
        state.setPath(System.getProperty("user.dir"));

        String[] twoFilesArgs = {"cat", first, first};
        String[] notFoundArgs = {"cat", "notExistingFile"};
        String[] fromStdinArgs = {"cat"};

        CatCommand twoFilesTest = new CatCommand(twoFilesArgs, state);
        CatCommand notFoundTest = new CatCommand(notFoundArgs, state);
        CatCommand fromStdinTest = new CatCommand(fromStdinArgs, state);
        CatCommand commands[] = {twoFilesTest, notFoundTest, fromStdinTest};

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
                () -> assertEquals(firstContent + firstContent, twoFilesTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.ERROR, notFoundTest.getCommandData().getStatus()),
                () -> assertEquals("cat : notExistingFile : No such file or directory\n", notFoundTest.getCommandData().getStderr().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, fromStdinTest.getCommandData().getStatus()),
                () -> assertEquals("First Line\nSecond line", fromStdinTest.getCommandData().getStdout().read())
        );
    }
}
