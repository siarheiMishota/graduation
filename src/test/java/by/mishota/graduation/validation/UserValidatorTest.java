package by.mishota.graduation.validation;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UserValidatorTest {

    @Test
    public void testValidateSignUp() {
        Map<String, String> stringStringMap = UserValidator.validateSignUp("aaa", "test@gmail.ru", "Password123", "Ivanov",
                "Ivan", "Petrov", "123321123", "2010-06-08", "female");
        assertTrue(stringStringMap.isEmpty());
    }

    @Test
    public void testValidateIncorrectEmailAndSimplePasswordAndGenderIncorrect() {
        Map<String, String> actual = new HashMap<>();
        Map<String, String> expected = UserValidator.validateSignUp("aaa", "testgmail.ru", "P123",
                "Ivanov", "Ivan", "Petrov", "123321123", "2010-06-08", "femal");
        actual.put("email", "emailIncorrect");
        actual.put("password", "simplePassword");
        actual.put("gender", "genderIncorrect");

        assertEquals(actual, expected);
    }

    @Test
    public void testValidateIncorrectEmailAndBirthIncorrect() {
        Map<String, String> actual = new HashMap<>();
        Map<String, String> expected = UserValidator.validateSignUp("aaa", "test@gmailru",
                "Password123", "Ivanov", "Ivan", "Petrov", "123321123",
                "2010-0608", "female");
        actual.put("email", "emailIncorrect");
        actual.put("birth", "birthIncorrect");
        assertEquals(actual, expected);
    }

    @Test
    public void testValidateIncorrectLengthLogin() {
        Map<String, String> actual = new HashMap<>();
        Map<String, String> expected = UserValidator.validateSignUp("aaasdasdasdasdasdasdasdwdqwdqwdasdasdasda",
                "test@gmail.ru",
                "Password123", "Ivanov", "Ivan", "Petrov", "123321123",
                "2010-06-08", "female");
        actual.put("login", "longLength");
        assertEquals(actual, expected);
    }

    @Test
    public void testValidateExtensionPhotoIncorrectEmailAndBirthInFuture() {
        Map<String, String> actual = new HashMap<>();
        Map<String, String> expected = UserValidator.validateSignUp("aaa", "test@gmail..ru",
                "Password123", "Ivanov",
                "Ivan", "Petrov", "123321123", "2021-06-08", "female");
        actual.put("email", "emailIncorrect");
        actual.put("birth", "birthInFuture");
        assertEquals(actual, expected);
    }


    @Test
    public void testValidateExtensionPhotoPng() {
        assertTrue(UserValidator.validateExtensionPhoto("photo.png").isEmpty());
    }

    @Test
    public void testValidateExtensionPhotoJpg() {
        assertTrue(UserValidator.validateExtensionPhoto("photo.jpg").isEmpty());
    }

    @Test
    public void testValidateExtensionPhotoJpeg() {
        assertTrue(UserValidator.validateExtensionPhoto("photo.jpeg").isEmpty());
    }

    @Test
    public void testValidateExtensionPhotoIncorrectPngg() {
        Map<String, String> actual = new HashMap<>();
        actual.put("photo", "photoIncorrectFormat");
        assertEquals(actual, UserValidator.validateExtensionPhoto("photo.pngg"));
    }

    @Test
    public void testValidateExtensionPhotoIncorrectJpEg() {
        Map<String, String> actual = new HashMap<>();
        actual.put("photo", "photoIncorrectFormat");
        assertEquals(actual, UserValidator.validateExtensionPhoto("photo.jpEg"));
    }

}