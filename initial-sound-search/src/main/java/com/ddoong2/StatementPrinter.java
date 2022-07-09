package com.ddoong2;

import java.util.Arrays;

public class StatementPrinter {

    public static final String HEADER = "Result";
    public static final String SEPARATOR = "-------------------------------------------------------";
    private Console console;

    public StatementPrinter(Console console) {
        this.console = console;
    }

    public void print(String[] datas) {
        console.printLine(HEADER);
        console.printLine(SEPARATOR);
        Arrays.stream(datas).forEach(console::printLine);
    }
}
