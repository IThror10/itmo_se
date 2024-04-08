package ru.itmo.cli.console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppStateTest {
    @Test
    void getSetVarTest() {
        //Arrange
        AppState state = new AppState();

        //Act
        state.setVar("var1", "value");
        String value = state.getVar("var1");
        state.setVar("var1", "other");
        String other = state.getVar("var1");
        String empty = state.getVar("nothing");

        //Assert
        assertEquals("value", value);
        assertEquals("other", other);
        assertEquals("", empty);
    }

    @Test
    void getSetPathTest() {
        //Arrange
        AppState state = new AppState();

        //Act
        state.setPath("Path/dir");
        String path = state.getPath();
        state.setPath("Path/dir/in");
        String changed = state.getPath();

        //Assert
        assertEquals("Path/dir", path);
        assertEquals("Path/dir/in", changed);
    }
}
