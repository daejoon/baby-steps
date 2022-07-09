package com.ddoong2.entitygraphtest;

import com.ddoong2.entitygraphtest.hello.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class EntityGraphTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntityGraphTestApplication.class, args);
    }

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Profile("data")
    @Bean
    public CommandLineRunner runner() {
        return args -> {
            Team myTeam = Team.builder()
                    .name("MyTeam")
                    .build();

            myTeam = teamRepository.save(myTeam);

            Member andew = Member.builder()
                    .firstName("Andew")
                    .lastName("Hong")
                    .team(myTeam)
                    .build();
            memberRepository.save(andew);

            Member brandon = Member.builder()
                    .firstName("Brandon")
                    .lastName("Kim")
                    .team(myTeam)
                    .build();
            memberRepository.save(brandon);

            Member charles = Member.builder()
                    .firstName("Charles")
                    .lastName("Kim")
                    .team(myTeam)
                    .build();
            memberRepository.save(charles);
        };
    }


    @GetMapping("/members")
    public ResponseEntity members() {
        ModelMapper modelMapper = new ModelMapper();

        List<Member> memberList = memberRepository.findByLastName("Kim");
        return ResponseEntity.ok(
                memberList.stream()
                        .map(member -> modelMapper.map(member, MemberDto.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/members-with-team")
    public ResponseEntity membersWithTeam() {
        ModelMapper modelMapper = new ModelMapper();

        List<Member> memberList = memberRepository.findByLastNameOrderById("Kim");
        return ResponseEntity.ok(
                memberList.stream()
                        .map(member -> modelMapper.map(member, MemberWithTeamDto.class))
                        .collect(Collectors.toList())
        );
    }
}
