package com.ddoong2.springbootunittest.fizzbuzz;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FizzBuzzTest {

    // 숫자를 3으로 나눌스 있다면 Fizz를 출력한다.
    // 숫자를 5로 나눌수 있다면 Buzz를 출력한다.
    // 숫자를 3이나 5로 나눌수 있다면 FizzBuzz를 출력한다.
    // 숫자를 3이나 5로 나눌수 없다면 숫자르로 그대로 출력한다.
    // 숫자의 범위는 1 ~ 100 이다.


    @Test
    @Order(1)
    @DisplayName("숫자를 3으로 나눌수 있다")
    void 숫자를_3으로_나눌수_있다() {
        fail("fail");
    }


}
