package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;

/**
 * This class represents the 'pwd' command which is used to print the current directory.
 * It extends the BaseCommand class and implements the execute method to perform the pwd command functionality.
 */
public class PwdCommand extends BaseCommand {
    private final String[] args;
    private final AppState state;

    /**
     * Constructor for the PwdCommand class that takes an array of arguments and the application state as parameters.
     *
     * @param args   Array of arguments passed to the pwd command
     * @param state  The current state of the application
     */
    public PwdCommand(String[] args, AppState state) {
        this.args = args;
        this.state = state;
    }

    /**
     * Executes the pwd command functionality by passing the current directory path to the Stdout descriptor.
     */
    @Override
    public void execute() {
        data.setStatus(CommandStatus.SUCCESS);
        getCommandData().getStdout().write(state.getPath());
    }
}
