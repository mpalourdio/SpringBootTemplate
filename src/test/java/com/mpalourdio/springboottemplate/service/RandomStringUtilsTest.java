package com.mpalourdio.springboottemplate.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RandomStringUtilsTest {

    public static final int LENGTH = 100;

    @Test
    public void randomAlphaNumeric() throws Exception {
        final String generated = RandomStringUtils.randomAlphaNumeric(LENGTH);
        Assert.assertTrue(generated.matches("^[a-z\\d]{" + LENGTH + "}$"));
    }

    @Test
    public void randomAlphabetic() throws Exception {
        final String generated = RandomStringUtils.randomAlphabetic(LENGTH);
        Assert.assertTrue(generated.matches("^[a-z]{" + LENGTH + "}$"));
    }

    @Test
    public void randomNumeric() throws Exception {
        final String generated = RandomStringUtils.randomNumeric(LENGTH);
        Assert.assertTrue(generated.matches("^\\d{" + LENGTH + "}$"));
    }
}
