package com.ddoong2.fixture_querydsl;

import com.ddoong2.fixture_querydsl.entity.User;
import com.ddoong2.fixture_querydsl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SpringBootApplication
public class FixtureQuerydslApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(FixtureQuerydslApplication.class, args);
	}

	@RequestMapping("/")
	public List<User> users() {
		return userRepository.getAllUsers();
	}
}
