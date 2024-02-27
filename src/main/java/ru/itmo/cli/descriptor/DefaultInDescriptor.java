package ru.itmo.cli.descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class DefaultInDescriptor implements IDescriptor {
    // Метод для записи данных во входной поток
    @Override
    public void write(String data) {
        // метод write() ничего не делает, так как входной поток используется только для чтения
    }

    // Метод для чтения данных из входного потока
    @Override
    public String read() {
        StringBuilder input = new StringBuilder();
        try {
            // Создание объекта BufferedReader для чтения данных из стандартного потока ввода
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            // Чтение строк из входного потока и добавление прочитанной строки в объект StringBuilder
            while ((line = reader.readLine()) != null) {
                input.append(line).append("\n");
            }
        } catch (IOException e) { // Обработка исключения IOException
        }
        return input.toString(); // Возвращение данных в виде строки

    }
}
