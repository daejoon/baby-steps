package com.ddoong2;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class ParserTest {

    private Parser parser;

    @Before
    public void setUp() throws Exception {
        parser = new Parser();
    }

    @Test
    public void 빈문자열은_검색결과_TRUE_이다() {
        assertThat(parser.find("", "가나다라 마바사")).isEqualTo(true);
    }

    @Test
    public void 검색_문자가_NULL_이라면_IllegalArgumentException_이_발생한다() {

        Throwable thrown = catchThrowable(() -> {
            parser.find(null, "가나다라 마바사");
        });

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 검색결과가_일치한다면_TRUE_일치하지_않으면_FALSE_를_리턴한다() {
        assertThat(parser.find("ㅎ", "가나다라 마바사")).isEqualTo(false);
        assertThat(parser.find("a", "가나다라 마바사")).isEqualTo(false);
        assertThat(parser.find("ㄱ", "가")).isEqualTo(true);
        assertThat(parser.find("ㄱㄴ", "가나")).isEqualTo(true);
        assertThat(parser.find("ㄹㅇ", "가나리오")).isEqualTo(true);
        assertThat(parser.find("ㄴㄷ", "가나 다라")).isEqualTo(false);
        assertThat(parser.find("ㄱㄴㄷㄹ ㅁ", "가나다라 마바사")).isEqualTo(true);
        assertThat(parser.find("ㄱㄴㄷㄹ ㅁ", "가나다라 마바사abcd")).isEqualTo(true);
        assertThat(parser.find("ㄱㄴㄷㄹ", "가나다")).isEqualTo(false);
    }
}