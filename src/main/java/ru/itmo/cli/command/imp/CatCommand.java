package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.FileDescriptor;


/**
 * cat command class
 * this command accepts the list of files, concatenate content of files and display it
 * if no files have been passed
 * command enters interactive mode and "repeat" every entered line until EOF is passed
 */
public class CatCommand extends BaseCommand {
    private final FileDescriptor[] files;

    public CatCommand(String[] args, AppState state) {
        this.files = new FileDescriptor[args.length - 1];
        for (int i = 0; i < this.files.length; i++) {
            this.files[i] = new FileDescriptor(args[i + 1]);
        }
    }

    @Override
    public void execute() {
        if (this.files.length > 0) {
            for (FileDescriptor fileDescriptor : this.files) {
                if (!fileDescriptor.found()) {
                    data.stderr().write(String.format("cat : %s : No such file or directory", fileDescriptor.getFileName()));
                } else {
                    String fileData = fileDescriptor.read();
                    data.stdout().write(fileData);
                }
            }
        } else {
            while (true) {
                String inputData = data.stdin().read();
                data.stdout().write(inputData);
            }
        }
    }
}
