package com.ddoong2.testprofiles;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class TestProfilesApplicationTests {

    @Autowired
    private Environment environment;

    @Value("${msg}")
    private String msg;

    @Test
    void contextLoads() {
        log.info("> Profiles = '{}'", environment.getActiveProfiles());
        log.info("> msg = '{}' ", msg);
    }

}
