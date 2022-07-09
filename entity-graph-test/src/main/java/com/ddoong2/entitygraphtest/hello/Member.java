package com.ddoong2.entitygraphtest.hello;

import lombok.*;

import javax.persistence.*;

@Getter @ToString @EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Builder
    public Member(String firstName, String lastName, Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
    }
}
