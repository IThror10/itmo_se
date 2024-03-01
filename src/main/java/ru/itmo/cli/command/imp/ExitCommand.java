package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;


/**
 * Exit command class
 * exits the program
 */
public class ExitCommand extends BaseCommand {
    private final String[] args;

    public ExitCommand(String[] args, AppState state) {
        this.args = args;
    }

    @Override
    public void execute() {
        if (args.length > 1) {
            data.getStderr().write("bash: exit: too many arguments");
            data.setStatus(CommandStatus.ERROR);
            return;
        }
        data.getStdin().write("exit");
        if (args.length == 1) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                data.getStdout().write(String.format("bash: exit: %s: numeric argument required", args[0]));
            }
        }
        data.setStatus(CommandStatus.EXIT);
    }
}
