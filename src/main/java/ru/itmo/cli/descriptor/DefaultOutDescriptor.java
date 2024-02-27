package ru.itmo.cli.descriptor;
import ru.itmo.cli.command.BaseCommand;
public class DefaultOutDescriptor implements IDescriptor {
    private final StringBuilder buffer = new StringBuilder(); // Буфер для хранения данных

    // Метод для записи строки в буфер, вместо непосредственной записи в стандартный поток вывода
    @Override
    public void write(String data) {
        buffer.append(data).append("\n"); // Добавление данных в буфер
    }

    // Метод для чтения и очистки буфера
    @Override
    public String read() {
        String result = buffer.toString(); // Получение текущего содержимого буфера
        buffer.setLength(0); // Очистка буфера
        return result; // Возвращение данных из буфера
    }
}
