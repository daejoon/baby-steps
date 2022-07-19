# Spring Boot Profile 전략

> 여러가지 방법이 있지만 경우의 수가 많고 머리 아프기 때문에 아래와 같이 진행하면 웬만한 복잡한 설정은 정리된다.

## 프로파일 로딩 순서

* 결국 마지막에 읽는게 우선 순위가 높다. 8번의 경우
* msg 라는 key가 존재한다면 순서는 아래와 같다.

1. application.yml `msg 값`
2. application.yml 안의 spring.config.import 첫번째 `msg 값`
3. application.yml 안의 spring.config.import 두번째 `msg 값`
4. application.yml 안의 spring.config.import n번째 `msg 값`
5. application-{profile}.yml `msg 값`
6. application-{profile}.yml 안의 spring.config.import 첫번째 `msg 값`
7. application-{profile}.yml 안의 spring.config.import 두번째 `msg 값`
8. application-{profile}.yml 안의 spring.config.import n번째 `msg 값`




