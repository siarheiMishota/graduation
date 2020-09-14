package by.mishota.graduation.util;

import org.testng.annotations.Test;

import java.security.NoSuchAlgorithmException;

import static org.testng.Assert.assertEquals;

public class Md5UtilTest {

    @Test
    public void testGenerateHashMd5() throws NoSuchAlgorithmException {
        String actual = "63a9f0ea7bb98050796b649e85481845";
        String expected = Md5Util.generateHashMd5("root");
        assertEquals(actual, expected);
    }

    @Test
    public void testGenerateHashMd5Two() throws NoSuchAlgorithmException {
        String actual = "74b8733745420d4d33f80c4663dc5e5";
        String expected = Md5Util.generateHashMd5("aaaa");
        assertEquals(actual, expected);
    }

    @Test
    public void testGenerateHashMd5Three() throws NoSuchAlgorithmException {

        System.out.println(Md5Util.generateHashMd5("r"));
//        String actual="eb9279982226a42afdf286dbdc29b45";
//        String expected=Md5Util.generateHashMd5("rrrr");
//        assertEquals(actual,expected);
    }

}