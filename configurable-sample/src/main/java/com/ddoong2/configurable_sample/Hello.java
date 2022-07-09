package com.ddoong2.configurable_sample;

import com.ddoong2.configurable_sample.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable(preConstruction = true)
public class Hello {

    @Autowired
    private FooService fooService;

    public FooService getFooService() {
        return fooService;
    }
}
