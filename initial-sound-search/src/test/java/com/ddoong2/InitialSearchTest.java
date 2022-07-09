package com.ddoong2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class InitialSearchTest {

    @Mock
    private Repository repository;
    @Mock
    private StatementPrinter statementPrinter;


    private InitialSearch initialSearch;

    @Before
    public void setUp() throws Exception {
        initialSearch = new InitialSearch(repository, statementPrinter);
    }

    @Test
    public void 데이터를_저장한다() {
        initialSearch.addData("가나다라 마바사");
        initialSearch.addData("가나리");

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository).addData("가나다라 마바사");
        inOrder.verify(repository).addData("가나리");
    }

    @Test(expected = IllegalArgumentException.class)
    public void null을_입력하면_IllegalArgumentException을_발생시킨다() {
        initialSearch.addData("가나다라 마바사");
        initialSearch.addData("가나리");
        initialSearch.addData("갈날리랑");
        initialSearch.addData("가나다라 마바사1");
        initialSearch.addData("가나다라 마바사2");

        initialSearch.search(null);
    }

    @Test
    public void 빈문자열로_검색하면_저장된_모든_데이터를_출력한다() {
        initialSearch.addData("가나다라 마바사");
        initialSearch.addData("가나리");
        String[] datas = {
                "가나다라 마바사",
                "가나리"
        };
        given(repository.search("")).willReturn(datas);

        initialSearch.search("");
        initialSearch.result();

        then(statementPrinter).should().print(datas);
    }

    @Test
    public void 검색조건에_맞는_데이터를_출력한다() {
        initialSearch.addData("가나다라 마바사");
        initialSearch.addData("가나리");
        String[] datas = {
                "가나다라 마바사",
                "가나리"
        };
        given(repository.search("ㄱㄴ")).willReturn(datas);

        initialSearch.search("ㄱㄴ");
        initialSearch.result();

        then(statementPrinter).should().print(datas);
    }
}