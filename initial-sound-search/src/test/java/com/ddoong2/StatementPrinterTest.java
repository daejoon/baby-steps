package com.ddoong2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class StatementPrinterTest {

    @Mock
    private Console console;

    private StatementPrinter statementPrinter;

    @Before
    public void setUp() throws Exception {
        statementPrinter = new StatementPrinter(console);
    }

    @Test
    public void 검색조건이_없는_리스트를_출력한다() {
        // given
        String[] notSearches = new String[0];

        // when
        statementPrinter.print(notSearches);

        // then
        InOrder inOrder = inOrder(console);
        inOrder.verify(console).printLine("Result");
        inOrder.verify(console).printLine("-------------------------------------------------------");
    }

    @Test
    public void 검색조건이_있는_리스트를_출력한다() {
        // given
        String result = "Result";
        String delimiter = "-------------------------------------------------------";
        String search = "가나다라";
        String[] searches = {
                search
        };

        // when
        statementPrinter.print(searches);

        // then
        InOrder inOrder = inOrder(console);
        inOrder.verify(console).printLine(result);
        inOrder.verify(console).printLine(delimiter);
        inOrder.verify(console).printLine(search);
    }
}