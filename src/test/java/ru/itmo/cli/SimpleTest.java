package ru.itmo.cli;

import org.junit.jupiter.api.Test;
import ru.itmo.cli.console.AppState;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleTest {
    @Test
    void nullForNotExistingKeyTest() {
        AppState state = new AppState();
        assertEquals(null, state.getVar("VarName"));
    }
}
