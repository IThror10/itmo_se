package ru.itmo.cli.console;

import lombok.Data;
import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.command.CommandData;
import ru.itmo.cli.command.CommandFactory;
import ru.itmo.cli.command.imp.PassCommand;
import ru.itmo.cli.descriptor.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-интерпретатор пользовательского выражения
 */
public class Interpreter {
    /**
     * Выполняет скрипт, переданный в виде строки на определенном состоянии программы
     * Использует Parser для выделения токенов и выполнения подстановок
     *
     * @param script "echo data | wc | cat" (Скрипт, введенный пользоваталем. Пример -- echo data | wc | cat)
     * @param state (Полученное состояние консоли)
     * @return CommandData
     */
    public CommandData launch(String script, AppState state) {
        CommandFactory factory = CommandFactory.createCommandFactory();
        IDescriptor stderr = new DefaultOutDescriptor();
        IDescriptor stdout = null;
        IDescriptor descriptors[] = {
                new DefaultOutDescriptor(),  //In == [0]
                null,                       //Out == [1]
                null  //Err == [2]
        };

        BaseCommand result = null;
        for (String command: Parser.conveyorSplit(script)) {
            Args args = parseArgs(Parser.argsSplit(command), state);
            stdout = new DefaultOutDescriptor();
            descriptors[1] = stdout;
            descriptors[2] = stderr;

            String[] arrArgs = args.getArgs().toArray(new String[args.getArgs().size()]);
            result = arrArgs.length == 0 ? new PassCommand() : factory.createCommand(arrArgs, state);

            for (int i = 0; i < 3; i++)
                result.changeInOut(descriptors[i], i);
            for (Descriptor desc: args.getRedirect())
                result.changeInOut(desc.descriptor(), desc.fileId());
            result.execute();
            descriptors[0] = result.getCommandData().getStdout();
        }
        result.getCommandData().setStdout(stdout);
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
                        new FileDescriptor(args.get(i + 1), state.getPath())
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

    @Data
    static class Args {
        private List<Descriptor> redirect = new ArrayList<>();
        private List<String> args = new ArrayList<>();
    }

    record Descriptor(
            int fileId,
            IDescriptor descriptor
    ) { }
}
