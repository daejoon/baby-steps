package com.ddoong2.configurable_sample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class FooServiceImpl implements FooService {
    @Override
    public String hello() {
        return "@Configurable!";
    }
}
