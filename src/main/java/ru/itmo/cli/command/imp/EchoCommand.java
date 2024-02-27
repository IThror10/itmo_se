package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;

/**
 * Класс служит для вызова команды echo.
 * echo --- выводит на экран свой аргумент (или аргументы).
 */
public class EchoCommand extends BaseCommand {
    private final String[] arguments;
    private AppState state;
    
    public EchoCommand(String[] args, AppState state) {
        this.arguments = args;
        this.state = state;
    }

    @Override
    public void execute() {
        for (String argument : arguments) {
            getCommandData().getStdout().write(argument + " ");
        }
    }
}
