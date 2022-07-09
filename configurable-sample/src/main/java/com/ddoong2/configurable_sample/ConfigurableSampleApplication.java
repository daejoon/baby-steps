package com.ddoong2.configurable_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableSpringConfigured
@EnableLoadTimeWeaving
@SpringBootApplication
public class ConfigurableSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurableSampleApplication.class, args);
	}

	@RequestMapping("/")
	public String foo() {
		Hello hello = new Hello();
		return hello.getFooService().hello();
	}
}
