package com.mpalourdio.springboottemplate.service;

import org.apache.commons.text.RandomStringGenerator;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public final class RandomStringUtils {

    private RandomStringUtils() {
    }

    public static String randomAlphaNumeric(int length) {
        return new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .get()
                .generate(length)
                .toLowerCase();
    }

    public static String randomAlphabetic(int length) {
        return new RandomStringGenerator.Builder()
                .withinRange('a', 'z')
                .get()
                .generate(length);
    }

    public static String randomNumeric(int length) {
        return new RandomStringGenerator.Builder()
                .withinRange('0', '9')
                .get()
                .generate(length);
    }
}
