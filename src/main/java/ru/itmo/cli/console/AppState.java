package ru.itmo.cli.console;

import java.util.HashMap;
import java.util.Map;

/**
 * The AppState class represents the application state, which includes environment variables
 * and the current working directory path.
 */
public class AppState {
    private final Map<String, String> environmentVariables;
    private String currentPath;

    /**
     * Constructs an AppState object with default environment variables and the current working directory path.
     */
    public AppState() {
        this.environmentVariables = new HashMap<>();
        this.currentPath = System.getProperty("user.dir");
    }

    /**
     * Sets an environment variable with the specified key and value.
     *
     * @param key   The key of the environment variable.
     * @param value The value of the environment variable.
     */
    public void setVar(String key, String value) {
        environmentVariables.put(key, value);
    }

    /**
     * Retrieves the value of the environment variable associated with the specified key.
     *
     * @param key The key of the environment variable.
     * @return The value of the environment variable, or an empty string if the variable is not found.
     */
    public String getVar(String key) {
        return environmentVariables.getOrDefault(key, "");
    }

    /**
     * Sets the current working directory path.
     *
     * @param newPath The new path of the current working directory.
     */
    public void setPath(String newPath) {
        currentPath = newPath;
    }

    /**
     * Retrieves the current working directory path.
     *
     * @return The current working directory path.
     */
    public String getPath() {
        return currentPath;
    }
}
