package com.ddoong2.javatokotlin.dto.user.request;

public class UserCreateRequest {

  private String name;
  private Integer age;

  public UserCreateRequest(final String name, final Integer age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

}