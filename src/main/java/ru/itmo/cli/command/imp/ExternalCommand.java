package ru.itmo.cli.command.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;

/**
 * The ExternalCommand class is used to execute external commands.
 * If something unknown is entered, the interpreter calls an external program.
 */
public class ExternalCommand extends BaseCommand {
    private final String[] args;
    private final AppState state;

    /**
     * Constructs an ExternalCommand with the specified arguments and application state.
     * @param args The arguments for the external command
     * @param state The current application state
     */
    public ExternalCommand(String[] args, AppState state) {
        this.args = args;
        this.state = state;
    }

    /**
     * Executes the external command.
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
