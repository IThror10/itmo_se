package ru.itmo.cli.descriptor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class FileDescriptor implements IDescriptor {
    private final String fileName;
    // Конструктор класса, принимающий имя файла
    public FileDescriptor(String fileName) {
        this.fileName = fileName;
    }

    // Метод для записи данных в файл
    @Override
    public void write(String data) {
        try {
            FileWriter writer = new FileWriter(fileName); // Создание объекта FileWriter для записи данных в файл
            writer.write(data); // Запись данных в файл
            writer.close(); // Закрытие потока записи
        } catch (IOException e) { // Обработка исключения IOException
        }
    }

    // Метод для чтения данных из файла
    @Override
    public String read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName)); // Создание объекта BufferedReader для чтения данных из файла
            StringBuilder content = new StringBuilder(); // Создание объекта StringBuilder для сохранения данных из файла
            String line;
            // Чтение строк из файла и добавление строки в объект StringBuilder
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close(); // Закрытие потока чтения
            return content.toString(); // Возвращение данных в виде строки
        } catch (IOException e) { // Обработка исключения IOException
        }
        return null; // В случае возникновения ошибки, возвращает null
    }
}
