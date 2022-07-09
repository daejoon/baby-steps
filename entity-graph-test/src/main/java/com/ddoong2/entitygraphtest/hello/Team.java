package com.ddoong2.entitygraphtest.hello;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @ToString @EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @Builder
    public Team(String name) {
        this.name = name;
    }
}
