package ru.itmo.cli.console;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandData;
import ru.itmo.cli.command.CommandFactory;
import ru.itmo.cli.descriptor.FileDescriptor;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class InterpreterTest {
    @Mock
    private CommandFactory factory = Mockito.mock(CommandFactory.class);

    MockedStatic<CommandFactory> staticMocked;
    InterpreterTest() {
        staticMocked = Mockito.mockStatic(CommandFactory.class);
        staticMocked.when(() -> CommandFactory.createCommandFactory())
                .thenReturn(factory);
    }

    private class MockCommand extends BaseCommand {

        @Override
        public void execute() {

        }
    }
    @Test
    void callCreateCommandTest() throws IOException {
        //Mock
        AppState state = Mockito.mock(AppState.class);
        BaseCommand dropCommand = new MockCommand();
        BaseCommand nextCommand = new MockCommand();

        Mockito.when(state.getPath()).thenReturn(null);
        Mockito.when(factory.createCommand(any(String[].class), any(AppState.class)))
                .thenReturn(dropCommand)
                .thenReturn(dropCommand)
                .thenReturn(nextCommand)
                .thenReturn(dropCommand)
                .thenThrow(new RuntimeException("Nothing to produce"));

        //Arrange
        String[] toCreate = {"string"};
        File inFile = File.createTempFile("test-infile-", ".txt");
        inFile.deleteOnExit();
        File outFile = File.createTempFile("test-data-", ".txt");
        outFile.deleteOnExit();
        File errFIle = File.createTempFile("test-data-", ".txt");
        errFIle.deleteOnExit();

        Interpreter interpreter = new Interpreter();
        String redirectTestScript = String.format("drop < %s > %s 2> %s",
                inFile.getCanonicalPath(), outFile.getCanonicalPath(), errFIle.getCanonicalPath());
        String conveyorTestScript = "drop | next | drop";

        //Act
        CommandData redirectResult = interpreter.launch(redirectTestScript, state);
        FileDescriptor in = (FileDescriptor) redirectResult.getStdin();
        FileDescriptor out = (FileDescriptor) redirectResult.getStdout();
        FileDescriptor err = (FileDescriptor) redirectResult.getStderr();

        //Assert
        assertEquals(inFile.getCanonicalPath(), in.getFileName());
        assertEquals(outFile.getCanonicalPath(), out.getFileName());
        assertEquals(errFIle.getCanonicalPath(), err.getFileName());
        assertDoesNotThrow(() -> interpreter.launch(conveyorTestScript, state));
        assertThrows(RuntimeException.class, () -> factory.createCommand(toCreate, state));
    }
}
