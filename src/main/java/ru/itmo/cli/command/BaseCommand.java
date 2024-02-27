package ru.itmo.cli.command;

import lombok.Data;
import ru.itmo.cli.descriptor.IDescriptor;

@Data
public abstract class BaseCommand {
    private CommandData data; // Приватное поле для хранения данных команды\
    // Абстрактный метод для выполнения команды, должен быть реализован в подклассах
    public abstract void execute();
    // Метод для изменения входного/выходного дескриптора
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
    public CommandData getCommandData() {
        return data;
    }
}
