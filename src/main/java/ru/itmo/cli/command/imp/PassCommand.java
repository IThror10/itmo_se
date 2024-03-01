package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;

public class PassCommand extends BaseCommand {
    public PassCommand(String[] args, AppState state) {}
    public PassCommand() {}

    @Override
    public void execute() {
        getCommandData().setStatus(CommandStatus.SKIP);
    }
}
