package com.ddoong2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

    @Mock
    private Parser parser;

    private Repository repository;

    @Before
    public void setUp() throws Exception {
        repository = new Repository(parser);

        repository.addData("가나다라 마바사");
        repository.addData("가나리");
        repository.addData("갈날리랑");
    }

    @Test
    public void 빈문자열이면_모든것을_반환한다() {
        given(parser.find("", "가나다라 마바사")).willReturn(true);
        given(parser.find("", "가나리")).willReturn(true);
        given(parser.find("", "갈날리랑")).willReturn(true);

        String[] search = repository.search("");

        assertThat(search.length).isEqualTo(3);
    }

    @Test
    public void 결색결과_일치하지_않는다면_빈_배열을_반환한다() {
        String[] search = repository.search("ㅈㅈ");
        assertThat(search.length).isEqualTo(0);

        search = repository.search("ㅋㅋ");

        assertThat(search.length).isEqualTo(0);
    }

    @Test
    public void 검색에_일치한_데이터를_반환한다() {
        given(parser.find("ㄱㄴㄷㄹ", "가나다라 마바사")).willReturn(true);

        String[] search = repository.search("ㄱㄴㄷㄹ");

        assertThat(search.length).isEqualTo(1);
        assertThat(search[0]).isEqualTo("가나다라 마바사");
    }
}