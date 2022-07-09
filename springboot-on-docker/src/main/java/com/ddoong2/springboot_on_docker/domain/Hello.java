package com.ddoong2.springboot_on_docker.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Hello")
public class Hello {
    @Id
    private Long id;

    private String name;

    private int age;
}
