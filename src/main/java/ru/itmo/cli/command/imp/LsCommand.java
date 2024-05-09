package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LsCommand extends BaseCommand {
    private final AppState state;
    private final List<File> descriptors;

    public LsCommand(String[] args, AppState state) {
        this.state = state;
        descriptors = new ArrayList<>();
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                descriptors.add(new File(args[i]));
            }
        } else {
            descriptors.add(new File(state.getPath()));
        }
    }

    @Override
    public void execute() {
        data.setStatus(CommandStatus.SUCCESS);
        for(File desc: descriptors){
            Optional<File[]> files = Optional.ofNullable(desc.listFiles());
            if (files.isPresent()) {
                if (descriptors.size() > 1){
                    data.getStdout().write(desc + ":\n");
                }
                for (File file_ : files.get()) {
                    data.getStdout().write(file_.getName() + "\n");
                }
            } else {
                data.getStderr().write("ls: " + state.getPath() + ": No such file or directory\n");
                data.setStatus(CommandStatus.ERROR);
            }
        }
    }
}
