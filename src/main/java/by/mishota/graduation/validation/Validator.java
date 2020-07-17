package by.mishota.graduation.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Validator {
    public static final String PARAM_ATTRIBUTE_GENDER = "gender";
    private final static String ADMIN_LOGIN = "admin";
    private final static String ADMIN_PASSWORD = "admin";
    private final static String REGEX_EMAIL = "^([\\w\\-\\.]+)@((\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.)" +
            "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|\\d{1,3})(\\]?)$";
    private final static String REGEX_BIRTH = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    private final static String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
    public static final String MALE = "male";
    public static final String FEMALE = "female";

    public static final String PARAM_ATTRIBUTE_EMAIL = "email";
    public static final String VALUE_ATTRIBUTE_EMAIL_INCORRECT = "emailIncorrect";
    public static final String PARAM_ATTRIBUTE_PASSWORD = "password";
    public static final String VALUE_ATTRIBUTE_SIMPLE_PASSWORD = "simplePassword";
    public static final String PARAM_ATTRIBUTE_BIRTH = "birth";
    public static final String VALUE_ATTRIBUTE_BIRTH_INCORRECT = "birthIncorrect";
    public static final String VALUE_ATTRIBUTE_BIRTH_IN_FUTURE = "birthInFuture";
    public static final String PARAM_ATTRIBUTE_PHOTO = "photo";
    public static final String VALUE_ATTRIBUTE_GENDER_INCORRECT = "genderIncorrect";
    public static final String VALUE_ATTRIBUTE_PHOTO_INCORRECT_FORMAT = "photoIncorrectFormat";

    private Validator() {
    }


    public static Map<String, String> validateSignUp(String email, String password,
                                                     String birth, String gender, String namePhoto) {
        Map<String, String> result = new HashMap<>();


        if (!validateEmailRegex(email)) {
            result.put(PARAM_ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_EMAIL_INCORRECT);
        }

        if (!validateComplexPassword(password)) {
            result.put(PARAM_ATTRIBUTE_PASSWORD, VALUE_ATTRIBUTE_SIMPLE_PASSWORD);
        }


        if (!validateCorrectStringBirth(birth)) {
            result.put(PARAM_ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_BIRTH_INCORRECT);
        } else if (!validateBirthInPast(birth)) {
            result.put(PARAM_ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_BIRTH_IN_FUTURE);
        }

        if (!validateGender(gender)) {
            result.put(PARAM_ATTRIBUTE_GENDER, VALUE_ATTRIBUTE_GENDER_INCORRECT);
        }

//        if (!validateFileExtensionPhoto(namePhoto)) {
//            result.put(PARAM_ATTRIBUTE_PHOTO, VALUE_ATTRIBUTE_PHOTO_INCORRECT_FORMAT);
//        }//todo

        return result;


    }


    private static boolean validateComplexPassword(String password) {
        if (password != null) {
            return Pattern.matches(REGEX_PASSWORD, password);
        }
        return false;
    }

    private static boolean validateEmailRegex(String email) {
        if (email == null) {
            return false;
        }
        return Pattern.matches(REGEX_EMAIL, email);
    }

    private static boolean validateCorrectStringBirth(String birth) {
        if (birth == null) {
            return false;
        }

        try {
            LocalDate.parse(birth);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private static boolean validateBirthInPast(String birth) {

        if (birth == null) {
            return false;
        }

        LocalDate parseBirth;
        try {
            parseBirth = LocalDate.parse(birth);
        } catch (DateTimeParseException e) {
            return false;
        }
        return LocalDate.now().isAfter(parseBirth);
    }

    private static boolean validateFileExtensionPhoto(String name) {
        if (name != null && !name.isEmpty()) {
            String extension = name.substring(name.lastIndexOf('.'));
            if (".jpg".equals(extension) || ".jpeg".equals(extension)
                    || ".png".equals(extension)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateGender(String gender) {
        if (gender != null) {
            return MALE.equalsIgnoreCase(gender) || FEMALE.equalsIgnoreCase(gender);
        }
        return false;
    }
}
