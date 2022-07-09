package com.ddoong2.springboot_on_docker.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface HelloRepository extends CrudRepository<Hello, Long> {
}
