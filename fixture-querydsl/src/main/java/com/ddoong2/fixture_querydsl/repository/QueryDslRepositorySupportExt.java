package com.ddoong2.fixture_querydsl.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class QueryDslRepositorySupportExt extends QueryDslRepositorySupport {
    public QueryDslRepositorySupportExt(Class<?> domainClass) {
        super(domainClass);
    }

    @PersistenceContext
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }
}
