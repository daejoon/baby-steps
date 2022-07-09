package com.ddoong2.entitygraphtest.hello;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MemberDto {
    private Long id;
    private String firstName;
    private String lastName;
}
