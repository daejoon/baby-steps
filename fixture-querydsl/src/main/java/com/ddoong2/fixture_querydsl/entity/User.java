package com.ddoong2.fixture_querydsl.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity(name = "USER")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
}
