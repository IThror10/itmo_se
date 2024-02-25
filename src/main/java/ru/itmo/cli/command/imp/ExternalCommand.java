package ru.itmo.cli.command.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
     * Хотим вызвать несуществующие команды в другом месте.
     */
    @Override
    public void execute() {
        // weak quoting
        for (int i = 1; i < args.length; ++i) {
            args[i] = "'" + args[i] + "'";
        }
        try {
            File tempFile = File.createTempFile("external_output", ".txt");
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            processBuilder.redirectOutput(tempFile);
            Process process = processBuilder.start();
            process.waitFor();
            BufferedReader reader = new BufferedReader(new FileReader(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                // @TODO тут нужно что-то сделать с прочитанными данными (line)!!!
            }
            reader.close();
            tempFile.delete();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
