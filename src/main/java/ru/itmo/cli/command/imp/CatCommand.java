package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.FileDescriptor;

/**
 * Cat command class.
 * This command accepts a list of files, concatenates the content of the files, and displays it.
 * If no files have been passed, the command enters interactive mode and repeats every entered line until EOF is passed.
 */
public class CatCommand extends BaseCommand {
    private final FileDescriptor[] files;

    /**
     * Constructor for the CatCommand class.
     * Takes an array of arguments and the application state as parameters.
     * Creates file Descriptors for each received file.
     *
     * @param args  The arguments passed to the command.
     * @param state The current application state.
     */
    public CatCommand(String[] args, AppState state) {
        this.files = new FileDescriptor[args.length - 1];
        for (int i = 0; i < this.files.length; i++) {
            this.files[i] = new FileDescriptor(args[i + 1], state.getPath());
        }
    }

    /**
     * Executes the CatCommand.
     * Concatenates and passes the content of files to the stdout descriptor
     * or enters interactive mode if no files are provided.
     */
    @Override
    public void execute() {
        data.setStatus(CommandStatus.SUCCESS);
        if (this.files.length > 0) {
            for (FileDescriptor fileDescriptor : this.files) {
                if (!fileDescriptor.found()) {
                    data.getStderr().write(String.format("cat : %s : No such file or directory", fileDescriptor.getFileName()));
                    data.setStatus(CommandStatus.ERROR);
                } else {
                    String fileData = fileDescriptor.read();
                    data.getStdout().write(fileData);
                }
            }
        } else {
            while (true) {
                String inputData = data.getStdout().read();
                data.getStdout().write(inputData);
            }
        }
    }
}
