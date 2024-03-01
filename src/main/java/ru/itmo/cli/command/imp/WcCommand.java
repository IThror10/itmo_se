package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.FileDescriptor;

/**
 * This class represents the 'wc' command
 * which is used to display the number of lines, words, and characters in files or standard input.
 * It extends the BaseCommand class and implements the execute method to perform the wc command functionality.
 */
public class WcCommand extends BaseCommand {
    private final FileDescriptor[] files;

    /**
     * Constructor for the WcCommand class
     * Takes an array of arguments and the application state as parameters.
     * Creates file Descriptors for each received file.
     *
     * @param args   Array of arguments passed to the wc command
     * @param state  The current state of the application
     */
    public WcCommand(String[] args, AppState state) {
        this.files = new FileDescriptor[args.length - 1];
        for (int i = 0; i < this.files.length; i++) {
            this.files[i] = new FileDescriptor(args[i + 1]);
        }
    }

    /**
     * Executes the wc command
     * Counts the number of lines, words, and characters in each received file or in standard input.
     * Updates the CommandStatus based on the execution result.
     */
    @Override
    public void execute() {
        data.setStatus(CommandStatus.SUCCESS);
        if (this.files.length > 0) {
            int totalSymbolNumber = 0;
            int totalLineNumber = 0;
            int totalWordNumber = 0;
            for (FileDescriptor fileDescriptor : this.files) {
                if (!fileDescriptor.found()) {
                    data.getStderr().write(String.format("wc : %s : No such file or directory", fileDescriptor.getFileName()));
                    data.setStatus(CommandStatus.ERROR);
                } else {
                    String fileData = fileDescriptor.read();
                    int symbolNumber = fileData.length();
                    int lineNumber = fileData.split("\\n").length;
                    int wordNumber = fileData.split("\\s+").length;
                    totalLineNumber += lineNumber;
                    totalSymbolNumber += symbolNumber;
                    totalWordNumber += wordNumber;
                    data.getStdout().write(String.format("%s %s %s %s", lineNumber, wordNumber, symbolNumber, fileDescriptor.getFileName()));
                }
            }
            if (files.length > 1) {
                data.getStdout().write(String.format("%s %s %s total", totalLineNumber, totalWordNumber, totalSymbolNumber));
            }
        } else {
            while (true) {
                String inputData = data.getStdin().read();
                data.getStdout().write(inputData);
            }
        }
    }
}
