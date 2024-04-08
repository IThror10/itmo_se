package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;

import java.io.File;

public class CdCommand extends BaseCommand {
    private final String targetDirectory;
    private AppState state;

    public CdCommand(String[] args, AppState state) {
        if (args.length < 2) {
            this.targetDirectory = System.getProperty("user.home");
        } else {
            this.targetDirectory = args[1];
        }
        this.state = state;
    }

    public AppState getState() {
        return state;
    }

    public void setState(AppState state) {
        this.state = state;
    }
    @Override
    public void execute() {
        File newDirectory = new File(targetDirectory);

        if (newDirectory.isDirectory()) {
            getState().setPath(newDirectory.getAbsolutePath());
        } else {
            getData().getStdout().write("Неверный путь: " + targetDirectory + "\n");
        }
    }
}
