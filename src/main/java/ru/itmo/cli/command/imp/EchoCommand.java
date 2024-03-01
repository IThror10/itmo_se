package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;

/**
 * Echo command class.
 * This command accepts a list of arguments, concatenates them with spaces, and displays the result.
 */
public class EchoCommand extends BaseCommand {
    private final String[] arguments;

    /**
     * Constructor for EchoCommand.
     * Initializes the EchoCommand with the given arguments.
     *
     * @param args  The arguments passed to the command.
     * @param state The current application state.
     */
    public EchoCommand(String[] args, AppState state) {
        arguments = new String[args.length - 1];
        System.arraycopy(args, 1, arguments, 0, args.length - 1);
    }

    /**
     * Executes the EchoCommand.
     * Concatenates the arguments with spaces and passes the result to the stdout descriptor.
     */
    @Override
    public void execute() {
        data.setStatus(CommandStatus.SUCCESS);
        for (int i = 0; i < arguments.length; i++) {
            data.getStdout().write(arguments[i]);
            if (i != arguments.length - 1) {
                data.getStdout().write(" ");
            }
        }
    }
}