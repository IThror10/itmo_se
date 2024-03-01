package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;


/**
 * ExitCommand class for exiting the CLI application.
 */
public class ExitCommand extends BaseCommand {
    private final String[] args;

    /**
     * Constructor for ExitCommand. Initializes the command with the specified arguments.
     * @param args The arguments passed to the command
     * @param state The current application state
     */
    public ExitCommand(String[] args, AppState state) {
        this.args = new String[args.length - 1];
        System.arraycopy(args, 1, this.args, 0, args.length - 1);
    }

    /**
     * Executes the ExitCommand.
     * Updates the CommandStatus based on the execution result.
     * If the command was successfully executed, it sets the status to EXIT.
     */
    @Override
    public void execute() {
        if (args.length > 1) {
            data.getStderr().write("exit: too many arguments");
            data.setStatus(CommandStatus.ERROR);
            return;
        }
        if (args.length == 1) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                data.getStderr().write(String.format("exit: %s: numeric argument required", args[0]));
            }
        }
        data.getStdout().write("exit");
        data.setStatus(CommandStatus.EXIT);
    }
}
