package ru.itmo.cli.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    static private final String conveyorReg = "\\s*\\|(?=(?:[^'\"]*(?:'[^']*'\"[^']*')*)*(?:[^'\"]*'[^']*'[^'\"]*)*$)";
    static public List<String> conveyorSplit(String script) {
        return Arrays.stream(script.split(conveyorReg))
                .map(String::strip)
                .toList();
    }

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

    static public String substitute(String str, AppState state) {
        return str;
    }
}
