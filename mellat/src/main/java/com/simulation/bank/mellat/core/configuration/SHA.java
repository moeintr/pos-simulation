package com.simulation.bank.mellat.core.configuration;

import lombok.SneakyThrows;

import java.security.MessageDigest;

public class SHA {

    @SneakyThrows
    public static String getHash256(String text) {

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }

    @SneakyThrows
    public static String getHash512(String text) {

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(text.getBytes());
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }
    private SHA()
    {

    }
}
