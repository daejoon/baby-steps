package com.ddoong2.feature;

import com.ddoong2.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class PrintInitialSoundSearchResultTest {

    @Mock
    private Console console;
    private InitialSearch initialSearch;

    @Before
    public void setUp() throws Exception {
        Parser parser = new Parser();
        Repository repository = new Repository(parser);
        StatementPrinter statementPrinter = new StatementPrinter(console);

        initialSearch = new InitialSearch(repository, statementPrinter);
    }

    @Test
    public void 초성으로_검색한_결과를_출력한다() {

        initialSearch.addData("가나다라 마바사");
        initialSearch.addData("가나리");
        initialSearch.addData("갈날리랑");
        initialSearch.addData("가나다라 마바사1");
        initialSearch.addData("가나다라 마바사2");

        initialSearch.search("ㄱㄴ");
        initialSearch.result();

        InOrder inOrder = inOrder(console);
        inOrder.verify(console).printLine("Result");
        inOrder.verify(console).printLine("-------------------------------------------------------");
        inOrder.verify(console).printLine("가나다라 마바사");
        inOrder.verify(console).printLine("가나리");
        inOrder.verify(console).printLine("갈날리랑");
    }
}
