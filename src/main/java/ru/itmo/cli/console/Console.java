package ru.itmo.cli.console;

import ru.itmo.cli.descriptor.DefaultInDescriptor;
import ru.itmo.cli.descriptor.IDescriptor;

/**
 * Console class
 * Console is the entry point of the program
 * It reads commands entered by user and pass them to the interpreter which executes the commands
 */
public class Console {
    public void work() {
        AppState appState = new AppState();
        Interpreter interpreter = new Interpreter();
        IDescriptor descriptor = new DefaultInDescriptor();
        while (true) {
            String script = descriptor.read();
            descriptor = interpreter.launch(script, appState).getStdin();
        }
    }
}
