package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.FileDescriptor;

/**
 * wc command class
 * this command accepts the list of files
 * and display number of lines, number of words and number of symbols in each file
 * if more than one file was passed
 * it also display the sum of lines number, words number and symbols number in all files
 * if no files have been passed
 * command enters interactive mode
 */
public class WcCommand extends BaseCommand {
    private final FileDescriptor[] files;

    public WcCommand(String[] args, AppState state) {
        this.files = new FileDescriptor[args.length - 1];
        for (int i = 0; i < this.files.length; i++) {
            this.files[i] = new FileDescriptor(args[i + 1]);
        }
    }

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
