package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;

/**
 * Класс служит для вызова команды pwd.
 * pwd --- команда печати текущей директории.
 */
public class PwdCommand extends BaseCommand {
    private final String[] args;
    private final AppState state;
    
    public PwdCommand(String[] args, AppState state) {
        this.args = args;
        this.state = state;
    }

    @Override
    public void execute() {
        getCommandData().getStdout().write(state.getPath());
    }
}
