package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;

/**
 * Команда
 */
public class SetVarCommand extends BaseCommand {
    public SetVarCommand(String[] args, AppState state) {
        this.state = state;
        this.args = args;
    }

    /**
     * Добавляет переменную в State
     *
     * Ожидае
     */
    @Override
    public void execute() {
        getCommandData().setStatus(CommandStatus.ERROR);
        if (args.length > 1)
            getCommandData().getStderr().write("Wrong amount of arguments for .*=.* command");
        else {
            String[] split = args[0].split("=");
            if (split.length != 2)
                getCommandData().getStderr().write("Operator= must occur only once");
            else {
                state.setVar(split[0], split[1]);
                getCommandData().setStatus(CommandStatus.SUCCESS);
            }
        }
    }

    private final String[] args;
    private final AppState state;
}
