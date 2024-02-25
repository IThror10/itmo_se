package ru.itmo.cli.command.imp;

import ru.itmo.cli.App;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;

public class PwdCommand extends BaseCommand {
    private final String[] args;
    private final AppState state;
    public PwdCommand(String[] args, AppState state) {
        this.args = args;
        this.state = state;
    }
    @Override
    public void execute() {
        System.out.println(state.getPath());
    }
}
