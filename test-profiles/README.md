# Spring Boot Profile 전략

> 여러가지 방법이 있지만 경우의 수가 많고 머리 아프기 때문에 아래와 같이 진행하면 웬만한 복잡한 설정은 정리된다.

## 프로파일 로딩 순서

> 뒤로 갈수록 우선 순위가 높음

* application.yml 설정을 로드
* application.yml의 spring.config.import가 존재하면 차례대로(왼쪽에서 오른쪽으로) 읽는다.
    * application.yml의 설정과 중복이 되면 덮어쓴다.
* application-{profile}.yml 설정을 로드
    * 기존 중복된 설정이 있으면 덮어쓴다.
* application-{profile}.yml의 spring.config.import가 존재하면 차례대로(왼쪽에서 오른쪽으로) 읽는다.
    * application-{profile}.yml 의 설정과 중복이 되면 덮어쓴다.
    * 중복된 spring.config.import 파일이 있을때는 두번째 중복인 설정은 다시 읽지 않는다.


