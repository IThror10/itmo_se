package ru.itmo.cli.console;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс состояне --- хранит локальные переменные и путь текущей дериктории.
 * @param environmentVariables хранилище локальных переменных
 * @param currentPath путь до текущей дериктории
 */
public class AppState {
    private final Map<String, String> environmentVariables;
    private String currentPath;

    public AppState() {
        this.environmentVariables = new HashMap<>();
        this.currentPath = System.getProperty("user.dir");
    }
    
    /** 
     * @param key
     * @param value
     */
    public void setVar(String key, String value) {
        environmentVariables.put(key, value);
    }
    
    /**
     * @return значение по ключу, если ключа нет, то вернем пустую строку.
     */
    public String getVar(String key) {
        return environmentVariables.getOrDefault(key, "");
    }

    public void setPath(String newPath) {
        currentPath = newPath;
    }

    public String getPath() {
        return currentPath;
    }
}
