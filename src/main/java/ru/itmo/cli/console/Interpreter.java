package ru.itmo.cli.console;

import lombok.Data;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandData;
import ru.itmo.cli.command.CommandFactory;
import ru.itmo.cli.descriptor.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Interpreter {
    /** 
     * @param script
     * @param state
     * @return CommandData
     */
    public CommandData launch(String script, AppState state) {
        CommandFactory factory = CommandFactory.createCommandFactory();
        IDescriptor stderr = new DefaultOutDescriptor();
        IDescriptor descriptors[] = {
                new DefaultInDescriptor(),  //In == [0]
                null,                       //Out == [1]
                null  //Err == [2]
        };

        BaseCommand result = null;
        for (String command: Parser.conveyorSplit(script)) {
            Args args = parseArgs(Parser.argsSplit(command), state);
            descriptors[1] = new DefaultOutDescriptor();
            descriptors[2] = stderr;

            result = factory.createCommand(args.getArgs().toArray(new String[args.getArgs().size()]), state);
            for (Descriptor desc: args.getRedirect())
                result.changeInOut(desc.descriptor(), desc.fileId());
            result.execute();
            descriptors[0] = result.getCommandData().getStdout();
        }
        return result.getCommandData();
    }

    private Args parseArgs(List<String> args, AppState state) {
        args = args.stream()
                .map(str -> Parser.substitute(str, state))
                .collect(Collectors.toList());

        Args result = new Args();
        for (int i = 0; i < args.size(); i++) {
            if (!map.containsKey(args.get(i)))
                result.getArgs().add(args.get(i));
            else {
                result.getRedirect().add(new Descriptor(
                        map.get(args.get(i)),
                        new FileDescriptor(args.get(i + 1))
                ));
                i += 1;
            }
        }
        return result;
    }

    private final static HashMap<String, Integer> map = new HashMap<>();
    static {
        map.put("<", 0);
        map.put(">", 1);
        map.put("2>", 2);
    }
}

@Data
class Args {
    private List<Descriptor> redirect;
    private List<String> args;
}

record Descriptor(
        int fileId,
        IDescriptor descriptor
) { }
