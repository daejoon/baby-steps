package com.ddoong2.fixture_querydsl.repository;

import com.ddoong2.fixture_querydsl.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
}
