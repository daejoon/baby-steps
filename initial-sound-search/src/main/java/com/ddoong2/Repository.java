package com.ddoong2;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private List<String> dataList = new ArrayList<>();
    private Parser parser;

    public Repository(Parser parser) {
        this.parser = parser;
    }

    public void addData(String data) {
        dataList.add(data);
    }

    public String[] search(String search) {

        List<String> result = new ArrayList<>();

        for (String data : dataList) {
            if (parser.find(search, data)) {
                result.add(data);
            }
        }

        return result.toArray(new String[0]);
    }
}
