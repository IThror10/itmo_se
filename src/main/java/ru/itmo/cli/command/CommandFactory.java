package ru.itmo.cli.command;

import ru.itmo.cli.console.AppState;
import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * CommandFactory class represents a factory for creating command instances based on arguments.
 */
public class CommandFactory {
    private static CommandFactory instance; // Статическое поле для хранения единственного экземпляра класса
    private final ArrayList<String> regexList; // Список регулярных выражений для сопоставления с названием команды
    private final ArrayList<BiFunction<String[], AppState, BaseCommand>> constructorList; //Список конструкторов команд
    private BiFunction<String[], AppState, BaseCommand> external;

    /**
     * Private constructor of the CommandFactory class.
     */
    private CommandFactory() {
        regexList = new ArrayList<>();
        constructorList = new ArrayList<>();
    }

    /**
     * Static method to create the single instance of CommandFactory.
     * @return CommandFactory
     */
    public static synchronized CommandFactory createCommandFactory() {
        if (instance == null) {
            instance = new  CommandFactory();
        }
        return instance;
    }

    /**
     * Create a command instance based on the provided command name and command arguments
     * If there is no command with the received name, the external command is used.
     * @param args The arguments for creating the command
     * @param state The current application state
     * @return BaseCommand The created command instance
     */
    public BaseCommand createCommand(String[] args, AppState state) {
        String commandName = args[0];

        for (int i = 0; i < regexList.size(); i++) {
            if (commandName.matches(regexList.get(i))) {
                return constructorList.get(i).apply(args, state);
            }
        }
        return external.apply(args, state);
    }

    /**
     * Register a regular expression and a command constructor.
     * @param regex The regular expression for matching the command name
     * @param constructor The constructor function for creating the command
     */
    public void registerCommand(String regex, BiFunction<String[], AppState, BaseCommand> constructor) {
        regexList.add(regex);
        constructorList.add(constructor);
    }

    /**
     * Register an external command constructor.
     * @param constructor The constructor function for creating external commands
     */
    public void registerDefault(BiFunction<String[], AppState, BaseCommand> constructor) {
        external = constructor;
    }

}
