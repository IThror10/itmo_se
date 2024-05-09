package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.imp.CdCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CdTest {

    @Test
    void cdTest(){
        AppState state = new AppState();
        String[] arg1 = {"cd", "bin"};
        String[] arg2 = {"cd", ".."};
        String[] arg3 = {"cd", "gradle"};

        CdCommand[] commands = {
                new CdCommand(arg1, state),
                new CdCommand(arg2, state),
                new CdCommand(arg3, state)
        };

        for (BaseCommand command: commands) {
            command.changeInOut(new DefaultOutDescriptor(), 1);
            command.changeInOut(new DefaultOutDescriptor(), 2);
        }

        commands[0].execute();
        assertEquals(state.getPath(), System.getProperty("user.dir") + "/bin");
        commands[1].execute();
        assertEquals(state.getPath(), System.getProperty("user.dir"));
        commands[2].execute();
        assertEquals(state.getPath(), System.getProperty("user.dir") + "/gradle");

    }


}
