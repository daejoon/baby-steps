package com.ddoong2.entitygraphtest.hello;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByLastName(String lastName);

    @EntityGraph(attributePaths = "team")
    List<Member> findByLastNameOrderById(String lastName);
}
