package ru.itmo.cli.command;

import ru.itmo.cli.console.AppState;

import java.util.function.BiFunction;

public class CommandFactory {
    
    /** 
     * @return CommandFactory
     */
    public static CommandFactory createCommandFactory() {
        return null;
    }

    public BaseCommand createCommand(String[] args, AppState state) {
        return null;
    }

    public void registerCommand(String regex, BiFunction<String[], AppState, BaseCommand> constructor) {

    }
}
