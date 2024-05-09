package ru.itmo.cli.command.impl;

import org.junit.jupiter.api.Test;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.EchoCommand;
import ru.itmo.cli.command.imp.LsCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.DefaultOutDescriptor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LsTest {
    @Test
    void LsTest(){
        AppState state = new AppState();
        String[] args1 = {"ls", System.getProperty("user.dir") + "/gradle"};
        String[] args2 = {"ls", System.getProperty("user.dir") + "/src/main/java/ru/itmo/cli",
                System.getProperty("user.dir") + "/src/main/java/ru/itmo/cli/descriptor"};

        LsCommand[] commands = {
                new LsCommand(args1, state),
                new LsCommand(args2, state)
        };

        for (BaseCommand command: commands) {
            command.changeInOut(new DefaultOutDescriptor(), 1);
            command.changeInOut(new DefaultOutDescriptor(), 2);
        }

        commands[0].execute();
        commands[1].execute();

        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, commands[0].getCommandData().getStatus()),
                () -> assertEquals("wrapper\nlibs.versions.toml\n", commands[0].getCommandData().getStdout().read())
        );
        assertAll(
                () -> assertEquals(CommandStatus.SUCCESS, commands[1].getCommandData().getStatus()),
                () -> assertEquals(System.getProperty("user.dir") + "/src/main/java/ru/itmo/cli:\n" +
                        "descriptor\n" +
                        "App.java\n" +
                        "console\n" +
                        "command\n" +
                        System.getProperty("user.dir") +
                        "/src/main/java/ru/itmo/cli/descriptor:\n" +
                        "FileDescriptor.java\n" +
                        "DefaultOutDescriptor.java\n" +
                        "DefaultInDescriptor.java\n" +
                        "IDescriptor.java\n", commands[1].getCommandData().getStdout().read())
        );
    }
}
