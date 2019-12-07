package com.t4.LiveServer.util;

import java.util.Random;

public class Otp {

    public static String generateOTP() {
        // Using numeric values
        String numbers = "0123456789qwertyuiopasdfghjklzxcvbnm";

        Random random = new Random();

        String otp = "";
        for (int i = 0; i < 55; i++) {
            otp += numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }
}
