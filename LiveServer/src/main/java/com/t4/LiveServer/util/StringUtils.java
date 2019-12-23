package com.t4.LiveServer.util;

import java.util.Random;

public class StringUtils {

    public static String randomString() {
        String numbers = "qwertyuiopasdfghjklzxcvbnm";

        Random random = new Random();

        String txt = "";
        for (int i = 0; i < 50; i++) {
            txt += numbers.charAt(random.nextInt(numbers.length()));
        }
        return txt;
    }
}
