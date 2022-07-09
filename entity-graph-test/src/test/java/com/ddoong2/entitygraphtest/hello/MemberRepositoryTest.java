package com.ddoong2.entitygraphtest.hello;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager em;
    @Autowired
    private MemberRepository memberRepository;
    private Team team;
    private Member andew;
    private Member brandon;
    private Member charles;

    @BeforeEach
    void init() {
        team = Team.builder()
                .name("MyTeam")
                .build();

        andew = Member.builder()
                .firstName("Andew")
                .lastName("Hong")
                .team(team)
                .build();

        brandon = Member.builder()
                .firstName("Brandon")
                .lastName("Kim")
                .team(team)
                .build();

        charles = Member.builder()
                .firstName("Charles")
                .lastName("Kim")
                .team(team)
                .build();
    }

    @Test
    @DisplayName("test member를 lastname으로 조회한다")
    void test_member를_lastname으로_조회한다() throws Exception {
        em.persist(team);
        em.persist(andew);
        em.persist(brandon);
        em.persist(charles);

        List<Member> memberList = memberRepository.findByLastName("Kim");

        assertThat(memberList).hasSize(2);
    }

    @Test
    @DisplayName("test member를 lastname으로 조회하는데 team도 같이 조회한다")
    void test_member를_lastname으로_조회하는데_team도_같이_조회한다() throws Exception {
        em.persist(team);
        em.persist(andew);
        em.persist(brandon);
        em.persist(charles);

        List<Member> members = memberRepository.findByLastNameOrderById("Kim");

        assertThat(Hibernate.isInitialized(members.get(0).getTeam())).isEqualTo(true);
    }
}