QueryDslRepositorySupport for Spring boot 1.5.8.RELEASE
=======================================================

> 1.5.7.RELEASE에서 1.5.8.RELEASE로 올리고 나서
> QueryDslRepositorySupport 클래스에서 예외가 발생했다.

```sbtshell
Caused by: java.lang.IllegalArgumentException: EntityManager must not be null!

```

기존 소스를 분석해 보니 QueryDslRepositorySupport 클래스가 변경되었다.

## 1.5.7.RELEASE QueryDslRepositorySupport 소스
```sbtshell
	...
	@PersistanceContext
	public void setEntityManager(EntityManager entityManager) {

		Assert.notNull(entityManager, "EntityManager must not be null!");
		this.querydsl = new Querydsl(entityManager, builder);
		this.entityManager = entityManager;
	}
	...

```

## 1.5.8.RELEASE QueryDslRepositorySupport 소스
```sbtshell
	...
	public void setEntityManager(EntityManager entityManager) {

		Assert.notNull(entityManager, "EntityManager must not be null!");
		this.querydsl = new Querydsl(entityManager, builder);
		this.entityManager = entityManager;
	}
	...

```

## 조치사항
`QueryDslRepositorySupport`을 보강한 `QueryDslRepositorySupportExt`클래스를 추가


## 실행방법
```sbtshell
$java -jar fixture_querydsl-0.0.1-SNAPSHOT.jar
```