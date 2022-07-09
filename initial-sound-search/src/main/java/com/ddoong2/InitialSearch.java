package com.ddoong2;

public class InitialSearch {

    private Repository repository;
    private StatementPrinter statementPrinter;
    private String[] result;

    public InitialSearch(Repository repository, StatementPrinter statementPrinter) {
        this.repository = repository;
        this.statementPrinter = statementPrinter;
    }

    public void search(String search) {
        if (search == null) {
            throw new IllegalArgumentException();
        }

        result = repository.search(search);
    }

    public void result() {
        statementPrinter.print(result);
    }

    public void addData(String data) {
        repository.addData(data);
    }
}
