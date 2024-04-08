package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.GrepCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrepTest {
    private final String first = "src/test/resources/grepTestFile";
    @Test
    public void catExecuteTest() {
        //Arrange
        AppState state = new AppState();
        state.setPath(System.getProperty("user.dir"));

        String[] fewFilesGrepArgs = {"grep", "aaa", first, first};
        String[] insensitiveGrepArgs = {"grep", "-i", "aaA", first};
        String[] wholeWordGrepArgs = {"grep", "-w", "skip", first};
        String[] withAmountGrepArgs = {"grep", "-A", "1", "Data", first};
        String[] unknownOptionArgs = {"grep", "-X", "data", first};
        String[] fromStdinArgs = {"grep", "data"};

        GrepCommand fewFilesTest = new GrepCommand(fewFilesGrepArgs, state);
        GrepCommand insensitiveTest = new GrepCommand(insensitiveGrepArgs, state);
        GrepCommand wholeWordTest = new GrepCommand(wholeWordGrepArgs, state);
        GrepCommand withAmountTest = new GrepCommand(withAmountGrepArgs, state);
        GrepCommand unknownOptionTest = new GrepCommand(unknownOptionArgs, state);
        GrepCommand fromStdinTest = new GrepCommand(fromStdinArgs, state);
        GrepCommand[] commands = {fewFilesTest, insensitiveTest, wholeWordTest,
                withAmountTest, unknownOptionTest, fromStdinTest};

        for (BaseCommand command: commands) {
            command.changeInOut(new DefaultOutDescriptor(), 1);
            command.changeInOut(new DefaultOutDescriptor(), 2);
        }

        fromStdinTest.changeInOut(new DefaultOutDescriptor(), 0);
        fromStdinTest.getCommandData().getStdin().write("First data Line\nSecond line");

        //Act
        for (BaseCommand command: commands)
            command.execute();

        //Assert
        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, fewFilesTest.getCommandData().getStatus()),
                () -> assertEquals("data aaa\ndata aaa\n", fewFilesTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, insensitiveTest.getCommandData().getStatus()),
                () -> assertEquals("data aaa\nData AaA\n", insensitiveTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, wholeWordTest.getCommandData().getStatus()),
                () -> assertEquals("skip string\n", wholeWordTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, withAmountTest.getCommandData().getStatus()),
                () -> assertEquals("Data AaA\nsome string Data\nskip string\n", withAmountTest.getCommandData().getStdout().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.ERROR, unknownOptionTest.getCommandData().getStatus()),
                () -> assertEquals("grep: invalid key -X\n", unknownOptionTest.getCommandData().getStderr().read())
        );

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, fromStdinTest.getCommandData().getStatus()),
                () -> assertEquals("First data Line\n", fromStdinTest.getCommandData().getStdout().read())
        );
    }
}
