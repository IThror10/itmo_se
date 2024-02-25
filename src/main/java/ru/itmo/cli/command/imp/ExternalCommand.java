package ru.itmo.cli.command.imp;

import java.io.IOException;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;

/**
 * Класс служит для вызова внешних команд.
 * Если введено что-то, чего интерпретатор не знает -- вызов внешней программы.
 */
public class ExternalCommand extends BaseCommand {
    private final String[] args;
    private final AppState state;

    public ExternalCommand(String[] args, AppState state) {
        this.args = args;
        this.state = state;
    }

    /**
     * Создаем новый объект Runtime,
     * хотим вызвать несуществующие программы в другом месте.
     */
    @Override
    public void execute() {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec(args);
            pr.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
