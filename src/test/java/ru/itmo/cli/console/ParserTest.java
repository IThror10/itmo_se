package ru.itmo.cli.console;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    @Test
    void splitConveyorScriptTest() {
        //Arrange
        String insideQuotes = "\"some | string\" | ' other | input'";
        String simpleTest = "echo | data | other | end";
        String noConveyor = "some command to call";

        //Act
        List<String> insideQuotesResult = Parser.conveyorSplit(insideQuotes);
        List<String> simpleTestResult = Parser.conveyorSplit(simpleTest);
        List<String> noConveyorResult = Parser.conveyorSplit(noConveyor);

        //Assert
        assertEquals(2, insideQuotesResult.size());
        assertEquals("\"some | string\"", insideQuotesResult.get(0));
        assertEquals("' other | input'", insideQuotesResult.get(1));
        assertEquals(4, simpleTestResult.size());
        assertEquals("echo", simpleTestResult.get(0));
        assertEquals("data", simpleTestResult.get(1));
        assertEquals("other", simpleTestResult.get(2));
        assertEquals("end", simpleTestResult.get(3));
        assertEquals(1, noConveyorResult.size());
        assertEquals("some command to call", noConveyorResult.get(0));
    }

    @Test
    void splitArgsTest() {
        //Arrange
        String simpleCommand = "command arg1 arg2 arg3";
        String redirectCommand = "command>test.txt<res.txt 2> data.txt";
        String quotesCommand = "command \"arg1\"'arg2'";
        String everythingCommand = "command $arg1   \"$arg2\" > 'res.txt' < \"$from\" 2> to";
        String noCloseBracket = "command arg1 \"arg2'";
        String equalsCommand = "value=\"some String\"";

        //Act
        List<String> simpleCommandResult = Parser.argsSplit(simpleCommand);
        List<String> redirectCommandResult = Parser.argsSplit(redirectCommand);
        List<String> quotesCommandResult = Parser.argsSplit(quotesCommand);
        List<String> everythingCommandResult = Parser.argsSplit(everythingCommand);
        List<String> equalsCommandResult = Parser.argsSplit(equalsCommand);

        //Assert
        assertAll(
                () -> assertEquals(4, simpleCommandResult.size()),
                () -> assertEquals("command", simpleCommandResult.get(0)),
                () -> assertEquals("arg1", simpleCommandResult.get(1)),
                () -> assertEquals("arg2", simpleCommandResult.get(2)),
                () -> assertEquals("arg3", simpleCommandResult.get(3))
        );
        assertAll(
                () -> assertEquals(7, redirectCommandResult.size()),
                () -> assertEquals("command", redirectCommandResult.get(0)),
                () -> assertEquals(">", redirectCommandResult.get(1)),
                () -> assertEquals("test.txt", redirectCommandResult.get(2)),
                () -> assertEquals("<", redirectCommandResult.get(3)),
                () -> assertEquals("res.txt", redirectCommandResult.get(4)),
                () -> assertEquals("2>", redirectCommandResult.get(5)),
                () -> assertEquals("data.txt", redirectCommandResult.get(6))
        );
        assertAll(
                () -> assertEquals(2, quotesCommandResult.size()),
                () -> assertEquals("command", quotesCommandResult.get(0)),
                () -> assertEquals("\"arg1\"'arg2'", quotesCommandResult.get(1))
        );
        assertAll(
                () -> assertEquals(9, everythingCommandResult.size()),
                () -> assertEquals("command", everythingCommandResult.get(0)),
                () -> assertEquals("$arg1", everythingCommandResult.get(1)),
                () -> assertEquals("\"$arg2\"", everythingCommandResult.get(2)),
                () -> assertEquals(">", everythingCommandResult.get(3)),
                () -> assertEquals("'res.txt'", everythingCommandResult.get(4)),
                () -> assertEquals("<", everythingCommandResult.get(5)),
                () -> assertEquals("\"$from\"", everythingCommandResult.get(6)),
                () -> assertEquals("2>", everythingCommandResult.get(7)),
                () -> assertEquals("to", everythingCommandResult.get(8))
        );
        assertAll(
                () -> assertEquals(1, equalsCommandResult.size()),
                () -> assertEquals("value=\"some String\"", equalsCommandResult.get(0))
        );
        assertThrows(RuntimeException.class, () -> Parser.argsSplit(noCloseBracket), "Quote for \" isn't closed");
    }

    @Test
    void substituteTest() {
        //Mocks
        AppState state = Mockito.mock(AppState.class);
        Mockito.when(state.getVar("data")).thenReturn("result");
        Mockito.when(state.getVar("from")).thenReturn("result");
        Mockito.when(state.getVar("other")).thenReturn("");

        //Arrange
        String dollar = "$";
        String variable = "$data";
        String simple = "someString";
        String singleQuotes = "'from $ to $other'";
        String doubleQuotes = "\"'$from' $ to $other\"";
        String complicate = "res'$data'\"$data\"$data$";

        //Act
        String dollarResult = Parser.substitute(dollar, state);
        String variableResult = Parser.substitute(variable, state);
        String simpleResult = Parser.substitute(simple, state);
        String singleQuotesResult = Parser.substitute(singleQuotes, state);
        String doubleQuotesResult = Parser.substitute(doubleQuotes, state);
        String complicateResult = Parser.substitute(complicate, state);

        //Assert
        assertEquals("$", dollarResult);
        assertEquals("result", variableResult);
        assertEquals("someString", simpleResult);
        assertEquals("from $ to $other", singleQuotesResult);
        assertEquals("res$dataresultresult$", complicateResult);
        assertEquals("'result' $ to ", doubleQuotesResult);
    }
}
