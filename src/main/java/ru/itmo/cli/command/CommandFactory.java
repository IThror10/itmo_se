package ru.itmo.cli.command;

import ru.itmo.cli.console.AppState;
import java.util.ArrayList;
import java.util.function.BiFunction;

public class CommandFactory {
    private static CommandFactory instance; // Статическое поле для хранения единственного экземпляра класса
    private final ArrayList<String> regexList; // Список регулярных выражений для сопоставления с названием команды
    private final ArrayList<BiFunction<String[], AppState, BaseCommand>> constructorList; //Список конструкторов команд
    //Приватный конструктор класса
    private CommandFactory() {
        regexList = new ArrayList<>();
        constructorList = new ArrayList<>();
    }

    // Статический метод для создания единственного экземпляра CommandFactory
    public static synchronized CommandFactory createCommandFactory() {
        if (instance == null) {
            instance = new  CommandFactory();
        }
        return instance;
    }

    // Метод для создания экземпляра команды на основе переданных аргументов
    public BaseCommand createCommand(String[] args, AppState state) {
        // Извлекаем первый элемент массива аргументов
        String commandName = args[0];

        // Проверяем совпадение с элементами из списка regexList
        for (int i = 0; i < regexList.size(); i++) {
            if (commandName.equals(regexList.get(i))) {
                // Если регулярное выражение совпадает, возвращаем соответствующий конструктор
                return constructorList.get(i).apply(args, state);
            }
        }
        // Если не найдено совпадение, возвращаем конструктор для ExternalCommand
        return constructorList.get(regexList.size() - 1).apply(args, state);
    }

    // Метод для регистрации регулярного выражения и конструктора команды
    public void registerCommand(String regex, BiFunction<String[], AppState, BaseCommand> constructor) {
        regexList.add(regex);
        constructorList.add(constructor);
    }

}
