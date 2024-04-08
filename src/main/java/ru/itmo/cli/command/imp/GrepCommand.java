package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.FileDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Command that performs grep-like text search operations.
 * This command searches for a given pattern in text input (or files) and prints matching lines.
 */
public class GrepCommand extends BaseCommand {
    /**
     * Constructs a GrepCommand object with provided arguments.
     *
     * @param args  Command-line arguments for the grep command
     * @param state Application state to access paths and settings
     */

    public GrepCommand(String[] args, AppState state) {
        int i = 1;
        boolean insensitive = false, whole = false;
        String template = "";
        StringBuilder error = new StringBuilder();

        while (i < args.length && args[i].startsWith("-")) {
            if (args[i].equals("-i"))
                insensitive = true;
            else if (args[i].equals("-w"))
                whole = true;
            else if (!args[i].equals("-A"))
                error.append(String.format("grep: invalid key %s", args[i])).append("\n");
            else {
                i += 1;
                try {
                    amount = Integer.parseInt(args[i]);
                } catch (NumberFormatException e) {
                    error.append("grep: Invalid argument for -A\n");
                }
            }
            i += 1;
        }

        if (i >= args.length)
            error.append("grep : No pattern provided");
        else
            template = args[i];

        for (int j = i + 1; j < args.length; j++)
            files.add(new FileDescriptor(args[j], state.getPath()));

        if (error.length() > 0)
            errorMsg = error.toString();
        else {
            if (whole)
                template = String.format("\\b%s\\b", template);
            pattern = Pattern.compile(template, insensitive ? Pattern.CASE_INSENSITIVE : 0);
        }
    }

    /**
     * Executes the grep command logic.
     * This method performs the search operation and writes results to stdout or stderr.
     */
    @Override
    public void execute() {
        if (errorMsg != null) {
            data.setStatus(CommandStatus.ERROR);
            data.getStderr().write(errorMsg);
            return;
        }

        data.setStatus(CommandStatus.SUCCESS);
        if (this.files.size() == 0) {
            data.getStdout().write(grepFile(data.getStdin().read()));
            return;
        }

        for (FileDescriptor fileDescriptor : this.files) {
            if (fileDescriptor.found())
                data.getStdout().write(grepFile(fileDescriptor.read()));
            else {
                data.getStderr().write(String.format("grep : %s : No such file or directory\n", fileDescriptor.getFileName()));
                data.setStatus(CommandStatus.ERROR);
            }
        }
    }

    private String grepFile(String text) {
        StringBuilder result = new StringBuilder();
        int curPrint = 0;
        for (String line: text.split("\n")) {
            if (pattern.matcher(line).find())
                curPrint = amount + 1;
            if (curPrint > 0) {
                result.append(line).append("\n");
                curPrint -= 1;
            }
        }
        return result.toString();
    }

    private int amount = 0;
    private Pattern pattern = null;
    private String errorMsg = null;
    private final List<FileDescriptor> files = new ArrayList<>();
}
