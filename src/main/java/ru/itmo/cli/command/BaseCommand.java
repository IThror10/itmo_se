package ru.itmo.cli.command;

import lombok.Data;
import ru.itmo.cli.descriptor.IDescriptor;

/**
* BaseCommand class serves as the base class for all commands in the CLI application.
*/
@Data
public abstract class BaseCommand {
    protected CommandData data = new CommandData(); // Приватное поле для хранения данных команды\

    /**
     * Abstract method to execute the command.
     * Must be implemented by subclasses.
     */
    public abstract void execute();

    /**
     * Changes the input/output descriptor based on the provided number.
     * @param descriptor The descriptor to change to
     * @param number The number representing the descriptor (0 for stdin, 1 for stdout, 2 for stderr)
     */
    public final void changeInOut(IDescriptor descriptor, int number) {
        // Проверяем номер дескриптора
        if (number == 0) {
            getCommandData().setStdin(descriptor);
        } else if (number == 1) {
            getCommandData().setStdout(descriptor);
        } else if (number == 2){
            getCommandData().setStderr(descriptor);
        } else {
            // Если номер дескриптора недопустим, выбрасываем исключение
            throw new IllegalArgumentException("Недопустимый номер дескриптора: " + number);
        }
    }

    /**
     * Returns the CommandData object associated with the command.
     *
     * @return The CommandData object
     */
    public CommandData getCommandData() {
        return data;
    }
}
