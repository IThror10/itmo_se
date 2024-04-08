package ru.itmo.cli.console;

import ru.itmo.cli.command.CommandData;
import ru.itmo.cli.command.CommandFactory;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.command.imp.*;

import java.util.Scanner;

/**
 * Console class
 * Console is the entry point of the program
 * It reads commands entered by user and pass them to the interpreter which executes the commands
 */
public class Console {
    private final AppState appState;

    public Console() {
        this.appState = new AppState();
        CommandFactory factory = CommandFactory.createCommandFactory();
        factory.registerDefault(ExternalCommand::new);
        factory.registerCommand("cat", CatCommand::new);
        factory.registerCommand("echo", EchoCommand::new);
        factory.registerCommand("exit", ExitCommand::new);
        factory.registerCommand("pwd", PwdCommand::new);
        factory.registerCommand("wc", WcCommand::new);
        factory.registerCommand(".+=.*", SetVarCommand::new);
        factory.registerCommand("ls", LsCommand::new);
        factory.registerCommand("cd", CdCommand::new);
    }

    public void work() {
        this.appState.setPath(System.getProperty("user.dir"));
        Interpreter interpreter = new Interpreter();
        Scanner scanner = new Scanner(System.in);
        String script, result;
        CommandData data;
        while (true) {
            System.out.print("\n> ");
            script = scanner.nextLine();
            data = interpreter.launch(script, this.appState);
            if (data.getStatus() == CommandStatus.ERROR) {
                data.getStdin().read();
                result = data.getStderr().read() + data.getStdout().read();
            } else {
                result = data.getStdout().read();
            }
            System.out.print(result);
            if (data.getStatus() == CommandStatus.EXIT) {
                System.exit(0);
            }
        }
    }
}
