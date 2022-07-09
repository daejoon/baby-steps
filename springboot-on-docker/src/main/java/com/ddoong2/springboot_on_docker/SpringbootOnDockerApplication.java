package com.ddoong2.springboot_on_docker;

import com.ddoong2.springboot_on_docker.domain.Hello;
import com.ddoong2.springboot_on_docker.domain.HelloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
@RestController
public class SpringbootOnDockerApplication {

    private final HelloRepository helloRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootOnDockerApplication.class, args);
    }


    @GetMapping("/")
    public String hello() {

        return "Remote Debugging!!";
    }

    @GetMapping("/mysql")
    public String mysql() {
        Optional<Hello> hello = helloRepository.findById(1L);
        if (hello.isPresent()) {
            return hello.get().getName();
        }

        return "Data is null";
    }
}
