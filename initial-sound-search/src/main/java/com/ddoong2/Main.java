package com.ddoong2;

public class Main {

    public static void main(String[] args) {
        Repository repository = new Repository(new Parser());
        StatementPrinter statementPrinter = new StatementPrinter(new Console());
        InitialSearch initialSearch = new InitialSearch(repository, statementPrinter);

        initialSearch.addData("가나다라 마바사");
        initialSearch.addData("가나리");
        initialSearch.addData("갈날리랑");
        initialSearch.addData("가나다라 마바사1");
        initialSearch.addData("가나다라 마바사2");

        initialSearch.search(args[0]);

        initialSearch.result();
    }

}
