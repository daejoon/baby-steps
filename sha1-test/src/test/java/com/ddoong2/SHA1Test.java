package com.ddoong2;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class SHA1Test {

    @Test
    public void 이름이_다르지만_내용이_같으면_SHA1_결과는_같다() throws IOException, NoSuchAlgorithmException {
        String content1;
        String content2;

        try (InputStream test1 = ClassLoader.getSystemResourceAsStream("same/test1.txt");
             InputStream test2 = ClassLoader.getSystemResourceAsStream("same/test2.txt")) {

            content1 = SHA1.encryptSHA1(test1);
            content2 = SHA1.encryptSHA1(test2);
        }

        assertThat(content1).isEqualTo(content2);
    }

    @Test
    public void 이름은_같지만_내용이_다르다면_SHA1_결과는_다르다() throws IOException, NoSuchAlgorithmException {
        String content1;
        String content2;

        try (InputStream test1 = ClassLoader.getSystemResourceAsStream("same/test1.txt");
             InputStream test2 = ClassLoader.getSystemResourceAsStream("diff/test1.txt")) {

            content1 = SHA1.encryptSHA1(test1);
            content2 = SHA1.encryptSHA1(test2);
        }

        assertThat(content1).isNotEqualTo(content2);
    }
}