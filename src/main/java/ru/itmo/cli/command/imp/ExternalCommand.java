package ru.itmo.cli.command.imp;

import ru.itmo.cli.command.BaseCommand;
import ru.itmo.cli.console.AppState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExternalCommand extends BaseCommand {
    private final String[] args;
    private final AppState state;
    public ExternalCommand(String[] args, AppState state) {
        this.args = args;
        this.state = state;
    }

    @Override
    public void execute() {
        System.out.println("Error: Unknown command!");
    }
}
