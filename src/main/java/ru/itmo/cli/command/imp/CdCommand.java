package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CdCommand extends BaseCommand {
    private final AppState state;
    private final List<String> target;

    public CdCommand(String[] args, AppState state){
        this.state = state;
        target = new ArrayList<>();
        target.addAll(Arrays.asList(args).subList(1, args.length));
    }


    @Override
    public void execute() {
        if (target.size() > 1){
            data.getStderr().write("cd: Too many arguments\n");
            data.setStatus(CommandStatus.ERROR);
            return;
        }
        String newPath = target.get(0);
        if ("..".equals(newPath)) {
            File currentDirectory = new File(state.getPath());
            if (currentDirectory.getParent()!= null) {
                data.setStatus(CommandStatus.SUCCESS);
                File currentDirectoryParent = new File(currentDirectory.getParent());
                state.setPath(currentDirectoryParent.getAbsolutePath());
            } else {
                data.getStderr().write("cd: Can't go up to the root directory\n");
                data.setStatus(CommandStatus.ERROR);
            }
        } else {
            File newDirectory = new File(state.getPath(), newPath);
            if (newDirectory.exists() && newDirectory.isDirectory()) {
                state.setPath(newDirectory.getAbsolutePath());
                data.setStatus(CommandStatus.SUCCESS);
            } else {
                data.getStderr().write("The wrong path: " + newPath + "\n");
                data.setStatus(CommandStatus.ERROR);
            }
        }
    }
}
