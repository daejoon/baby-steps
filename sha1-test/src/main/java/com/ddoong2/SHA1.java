package com.ddoong2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA1 {
    private static final String ALGORITHM = "SHA1";
    private static final int BUFFER_SIZE = 1024;
    private static final int EOF = -1;

    private static MessageDigest createMessageDigestOfSHA1() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(ALGORITHM);
    }

    private static String getResultString(byte[] result) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            stringBuffer.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return stringBuffer.toString();
    }

    public static String encryptSHA1(String input) throws NoSuchAlgorithmException, IOException {
        return encryptSHA1(input.getBytes());
    }

    public static String encryptSHA1(byte[] input) throws NoSuchAlgorithmException, IOException {
        MessageDigest mDigest = createMessageDigestOfSHA1();
        byte[] result = mDigest.digest(input);
        return getResultString(result);
    }

    public static String encryptSHA1(InputStream input) throws NoSuchAlgorithmException, IOException {
        MessageDigest mDigest = createMessageDigestOfSHA1();

        byte[] buffer = new byte[BUFFER_SIZE];
        int read_len = EOF;
        while ((read_len = input.read(buffer)) != EOF) {
            mDigest.update(buffer, 0, read_len);
        }
        byte[] result = mDigest.digest();

        return getResultString(result);
    }

    public static String encryptSHA1(File input) throws NoSuchAlgorithmException, IOException {
        String result = null;
        try (FileInputStream fileInputStream = new FileInputStream(input)) {
            result = encryptSHA1(fileInputStream);
        }
        return result;
    }
}