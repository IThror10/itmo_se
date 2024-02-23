package ru.itmo.cli;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ru.itmo.cli.command.CommandFactory;

public class SingletonTest {
    @Mock
    private CommandFactory factory;

    MockedStatic<CommandFactory> staticMocked;
    SingletonTest() {
        try {
            staticMocked = Mockito.mockStatic(CommandFactory.class);
            staticMocked.when(CommandFactory::createCommandFactory).thenReturn(Mockito.mock(CommandFactory.class));
        } catch (Exception e) {}
        factory = CommandFactory.createCommandFactory();
    }

    @Test
    void createCommandTest() {
        //Mocks
//        AppState state = Mockito.mock(AppState.class);
//        BaseCommand command = Mockito.mock(BaseCommand.class);
//        doReturn(command).when(factory.createCommand(any(String[].class), state));

        //Arrange
//        Interpreter interpreter = new Interpreter();
//        String query = "touch";

        //Act
//        BaseCommand result = interpreter.parse(query);

        //Assert
//        assertAll(
//            ....
//        );
    }
}
