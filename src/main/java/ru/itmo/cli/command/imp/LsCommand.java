package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.FileDescriptor;

import java.io.File;

public class LsCommand extends BaseCommand {
    private final FileDescriptor[] files;

    public LsCommand(String[] args, AppState state) {
        String currentPath = state.getPath();
        File[] fileList = new File(currentPath).listFiles();
        if (fileList != null) {
            this.files = new FileDescriptor[fileList.length];
            for (int i = 0; i < fileList.length; i++) {
                this.files[i] = new FileDescriptor(fileList[i].getName(), currentPath);
            }
        } else {
            this.files = new FileDescriptor[0];
        }
    }

    @Override
    public void execute() {
        for (FileDescriptor fileDescriptor : files) {
            getData().getStdout().write(fileDescriptor.getFileName() + " ");
        }
        getData().getStdout().write("\n");
    }
}

