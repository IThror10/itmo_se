package ru.itmo.cli.command.imp;

import java.io.*;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandStatus;
import ru.itmo.cli.console.AppState;
import ru.itmo.cli.descriptor.IDescriptor;

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
        File tempIn = null, tempOut = null, tempErr = null;
        try {
            for (int i = 1; i < args.length; ++i)
                args[i] = "'" + args[i] + "'";

            tempIn = File.createTempFile("external_input", ".txt");
            tempOut = File.createTempFile("external_output", ".txt");
            tempErr = File.createTempFile("external_error", ".txt");

            FileWriter writer = new FileWriter(tempIn);
            writer.write(getCommandData().getStdin().read());
            writer.close();

            Process process = new ProcessBuilder(args)
                    .redirectInput(tempIn)
                    .redirectError(tempErr)
                    .redirectOutput(tempOut).start();
            process.waitFor();

            redirect(tempOut, getCommandData().getStdout(), CommandStatus.SUCCESS);
            redirect(tempErr, getCommandData().getStderr(), CommandStatus.ERROR);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            getCommandData().setStatus(CommandStatus.ERROR);
            getCommandData().getStderr().write("Something went wrong while executing\n\t " + args[0]);
        } finally {
            if (tempIn != null)
                tempIn.delete();
            if (tempOut != null)
                tempOut.delete();
            if (tempErr != null)
                tempErr.delete();
        }
    }

    private void redirect(File from, IDescriptor to, CommandStatus status) throws IOException {
        String line;
        StringBuilder res = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(from));

        while ((line = reader.readLine()) != null)
            res.append(line);

        if (res.length() > 0) {
            to.write(res.toString());
            getCommandData().setStatus(status);
        }
        reader.close();
    }
}
