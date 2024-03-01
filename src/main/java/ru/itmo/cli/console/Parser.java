package ru.itmo.cli.console;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The Parser class provides utility methods for parsing scripts and performing string substitutions.
 */
public class Parser {
    static private final String conveyorReg = "\\s*\\|(?=(?:[^'\"]*(?:'[^']*'\"[^']*')*)*(?:[^'\"]*'[^']*'[^'\"]*)*$)";
    /**
     * Splits a script into segments based on the conveyor separator '|'.
     *
     * @param script The script to be split.
     * @return A list of script segments.
     */
    static public List<String> conveyorSplit(String script) {
        return Arrays.stream(script.split(conveyorReg))
                .map(String::strip)
                .toList();
    }

    /**
     * Splits a script into individual arguments.
     *
     * @param script The script to be split into arguments.
     * @return A list of individual arguments.
     */
    static public List<String> argsSplit(String script) {
        List<String> args = new ArrayList<>();
        StringBuilder acc = new StringBuilder();
        for (int cur = 0; cur < script.length(); ++cur) {
            char sym = script.charAt(cur);
            switch (sym) {
                case '\"', '\'' -> {
                    int to = script.indexOf(sym, cur + 1) + 1;
                    if (to == 0)
                        throw new RuntimeException("Quote for " + sym + " isn't closed");
                    acc.append(script, cur, to);
                    cur = to - 1;
                }
                case ' ' -> {
                    args.add(acc.toString());
                    acc.delete(0, acc.length());
                }
                case '>' -> {
                    if (cur == 0 || script.charAt(cur - 1) != '2') {
                        args.add(acc.toString());
                        args.add(">");
                    } else {
                        args.add(acc.substring(0, acc.length() - 1));
                        args.add("2>");
                    }
                    acc.delete(0, acc.length());
                }
                case '<' -> {
                    args.add(acc.toString());
                    args.add("<");
                    acc.delete(0, acc.length());
                }
                default -> acc.append(sym);
            }
        }
        args.add(acc.toString());

        return args.stream()
                .map(String::strip)
                .filter(str -> str.length() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Performs string substitution in a script based on the application state.
     *
     * @param str   The script segment to be substituted.
     * @param state The application state containing environment variables.
     * @return The script segment with substitutions applied.
     */
    static public String substitute(String str, AppState state) {
        Pattern outer = Pattern.compile("\"[^\"]*\"|'[^']*'|\\$[^\"'\\s\\$]+|[^\"^'$]+|\\$+");
        Matcher outMatch = outer.matcher(str);
        StringBuilder builder = new StringBuilder();

        while (outMatch.find()) {
            String cur = outMatch.group();
            if (cur.startsWith("'"))
                builder.append(cur, 1, cur.length() - 1);
            else if (!cur.startsWith("\""))
                builder.append(dollarSubstitute(cur, state));
            else {
                Pattern pattern = Pattern.compile("\\$[a-zA-Z1-9]*|[^$]+");
                Matcher matcher = pattern.matcher(cur.substring(1, cur.length() - 1));
                StringBuilder local = new StringBuilder();

                while (matcher.find())
                    local.append(dollarSubstitute(matcher.group(), state));
                builder.append(local);
            }
        }
        return builder.toString();
    }

    static private String dollarSubstitute(String str, AppState state) {
        if (str.charAt(0) == '$') {
            if (str.length() == 1 || str.charAt(1) == ' ')
                return str;
            return state.getVar(str.substring(1));
        }
        return str;
    }
}
