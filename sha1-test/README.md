# SHA1 TEST
SHA1는 암호화 알고리즘의 한가지 방법이다. [참고문서](https://ko.wikipedia.org/wiki/SHA)

### 중요한 특징
- 암호화한 결과물은 16진수의 40자리수로 나타난다.
- 내용이 같다면 같은 결과물을 기대할수 있다.

위 특징으로 [Git](https://git-scm.com/) 에서 사용하기도 한다. 
그리고 이런 특징으로 파일이나 특정 데이터의 정합성이나 무결성 체크에 사용하기 좋다.


## 실행방법
```
$ ./gradlew clean test
```