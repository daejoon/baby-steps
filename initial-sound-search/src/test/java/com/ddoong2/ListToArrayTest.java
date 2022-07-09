package com.ddoong2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ListToArrayTest {

    @Test
    public void 리스트를_배열로_변환한때_인자로_넘기는_개수가_많으면_그_개수만큼_배열이_변환된다() {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");

        assertThat(data.toArray(new String[5]).length).isEqualTo(5);
    }

    @Test
    public void 리스트를_배열로_변환한때_인자로_넘기는_개수가_리스트_개수보다_작다면_리스트의_사이즈만큼_배열이_변환된다() {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");

        assertThat(data.toArray(new String[2]).length).isEqualTo(3);
    }

    @Test
    public void 리스트를_배열로_변환한때_인자로_넘기는_개수가_많으면_그_나머지는_NULL로_채워진다() {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");

        String[] array = data.toArray(new String[5]);

        assertThat(array[2]).isEqualTo("C");
        assertThat(array[3]).isNull();
        assertThat(array[4]).isNull();
    }
}
