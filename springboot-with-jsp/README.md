# Spring Boot With Jsp
> `Spring Boot 1.5.22.RELEASE`와 Jsp와 같이 사용해보는 프로젝트

## Environment
- Java 1.8
- Spring Boot 1.5.22.RELEASE
- Gradle 4.10.2

## 설정 사항
#### build.gradle 추가
```
...
war {
	baseName = 'springboot-with-jsp'
	version = '1.0.0'
	webAppDirName = 'src/main/webapp'
}
...
dependencies {
	compile 'org.springframework.boot:spring-boot-starter-web'
	compile 'org.springframework.boot:spring-boot-starter-tomcat'
	compile 'javax.servlet:jstl'
	compile 'org.apache.tomcat.embed:tomcat-embed-jasper'
	testCompile 'org.springframework.boot:spring-boot-starter-test'
}
```
- `war`로 생성해야 한다. `jar`로 생성하면 안된다. ([JSP 제한사항](https://docs.spring.io/spring-boot/docs/1.5.4.RELEASE/reference/htmlsingle/#boot-features-jsp-limitations))
- `org.apache.tomcat.embed:tomcat-embed-jasper`을 `compleOnly`로 설정했더니 java -jar로 실행시 jsp 파일을 못찾는다. `complie`로 한다.

#### `src/webapp/WEB-INF/jsp` 디렉토리 생성
```
$ mkdir -p src/webapp/WEB-INF/jsp
```

#### application.properties 설정
```
...
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
...
```
- `/WEB-INF/jsp/`는 JSTL, JASPER 기본으로 사용하는 위치 변경하면 손이 많이 간다. 그대로 사용.

## 빌드 및 실행
#### Test
```
$ ./gradlew clean test
```

#### Build
```
$ ./gradlew clean build
```

#### Run
```
$ java -jar build/libs/springboot-with-jsp-1.0.0.war
```
or
```
$ ./gradlew bootRon
```

## 참고
- [JSP 제한사항](https://docs.spring.io/spring-boot/docs/1.5.4.RELEASE/reference/htmlsingle/#boot-features-jsp-limitations)
- [JSP Sample](https://github.com/spring-projects/spring-boot/tree/v1.5.22.RELEASE/spring-boot-samples/spring-boot-sample-web-jsp)
