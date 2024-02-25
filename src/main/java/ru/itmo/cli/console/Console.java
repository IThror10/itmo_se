package ru.itmo.cli.console;

import ru.itmo.cli.command.imp.ExternalCommand;

import java.util.Scanner;

public class Console {
    public void work() {
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            // Вывод приглашения для ввода
            System.out.print("> ");
            // Чтение ввода пользователя
            input = scanner.nextLine();
            // Разбор и выполнение команды
            // TODO
            // Проверка на команду выхода
        } while (!input.trim().equalsIgnoreCase("exit"));

        scanner.close();
    }
}
