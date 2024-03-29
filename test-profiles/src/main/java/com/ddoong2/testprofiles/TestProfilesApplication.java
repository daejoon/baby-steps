package com.ddoong2.testprofiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;


@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class TestProfilesApplication implements ApplicationRunner {

    private final Environment environment;

    @Value("${msg}")
    private String msg;

    @Value("${external-default-only}")
    private String externalDefaultOnly;

    public static void main(String[] args) {
        SpringApplication.run(TestProfilesApplication.class, args);
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        log.info("### Profiles = '{}'", environment.getActiveProfiles());
        log.info("### msg = '{}'", msg);
        log.info("### externalDefaultOnly = '{}'", externalDefaultOnly);
    }
}
