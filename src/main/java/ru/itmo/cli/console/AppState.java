package ru.itmo.cli.console;

import java.util.HashMap;
import java.util.Map;

public class AppState {
    private final Map<String, String> environmentVariables;
    private String currentPath;

    public AppState() {
        this.environmentVariables = new HashMap<>();
        this.currentPath = System.getProperty("user.dir");
    }
    public void setVar(String key, String value) {
        environmentVariables.put(key, value);
    }
    public String getVar(String key) {
        return environmentVariables.get(key);
    }

    public void setPath(String newPath) {
        currentPath = newPath;
    }

    public String getPath() {
        return currentPath;
    }
}
