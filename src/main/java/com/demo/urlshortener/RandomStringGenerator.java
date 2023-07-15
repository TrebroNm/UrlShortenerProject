package com.demo.urlshortener;
import java.util.Random;

public class RandomStringGenerator {

    //Defines which characters can be used to generate the random string
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    private RandomStringGenerator() {
        // Private constructor to prevent instantiation
    }


    //Generates a random string
    public static String generate(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}