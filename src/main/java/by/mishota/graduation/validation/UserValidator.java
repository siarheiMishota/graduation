package by.mishota.graduation.validation;

import by.mishota.graduation.controller.Attribute;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static by.mishota.graduation.controller.Attribute.*;

public class UserValidator {
    private final static String REGEX_EMAIL = "^([\\w\\-\\.]+)@((\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.)" +
            "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|\\d{1,3})(\\]?)$";
    private final static String REGEX_BIRTH = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
    private final static String REGEX_PASSWORD = "^(?=.*[A-Za-z а-я А-Я])(?=.*\\d)[A-Za-z а-я А-Я\\d]{8,}$";
    private static final int MAX_LENGTH_FIELD = 40;


    private UserValidator() {
    }


    public static Map<String, String> validateSignUp(String login, String email, String password, String surname,
                                                     String firstName, String fatherName, String passportId,
                                                     String birth, String gender) {
        Map<String, String> result = new HashMap<>();

        if (login == null) {
            result.put(Attribute.ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_NULL);
        } else if (login.length() > 25) {
            result.put(Attribute.ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (email == null) {
            result.put(Attribute.ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_NULL);
        } else if (email.length() > MAX_LENGTH_FIELD) {
            result.put(Attribute.ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_LONG_LENGTH);
        } else if (!validateEmailRegex(email)) {
            result.put(Attribute.ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_EMAIL_INCORRECT);
        }

        if (password == null) {
            result.put(ATTRIBUTE_PASSWORD, VALUE_ATTRIBUTE_NULL);
        } else if (password.length() > MAX_LENGTH_FIELD) {
            result.put(ATTRIBUTE_PASSWORD, VALUE_ATTRIBUTE_LONG_LENGTH);
        } else if (!validateComplexPassword(password)) {
            result.put(ATTRIBUTE_PASSWORD, VALUE_ATTRIBUTE_SIMPLE_PASSWORD);
        }

        if (birth == null) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_NULL);
        } else if (birth.length() > MAX_LENGTH_FIELD) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_LONG_LENGTH);
        } else if (!validateCorrectStringBirth(birth)) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_BIRTH_INCORRECT);
        } else if (!validateBirthInPast(birth)) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_BIRTH_IN_FUTURE);
        }

        if (surname == null) {
            result.put(ATTRIBUTE_SURNAME, VALUE_ATTRIBUTE_NULL);
        } else if (surname.length() > MAX_LENGTH_FIELD) {
            result.put(ATTRIBUTE_SURNAME, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (firstName == null) {
            result.put(ATTRIBUTE_FIRST_NAME, VALUE_ATTRIBUTE_NULL);
        } else if (firstName.length() > MAX_LENGTH_FIELD) {
            result.put(ATTRIBUTE_FIRST_NAME, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (fatherName == null) {
            result.put(ATTRIBUTE_FATHER_NAME, VALUE_ATTRIBUTE_NULL);
        } else if (fatherName.length() > MAX_LENGTH_FIELD) {
            result.put(ATTRIBUTE_FATHER_NAME, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (passportId == null) {
            result.put(Attribute.ATTRIBUTE_PASSPORT_ID, VALUE_ATTRIBUTE_NULL);
        } else if (passportId.length() > MAX_LENGTH_FIELD) {
            result.put(Attribute.ATTRIBUTE_PASSPORT_ID, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (gender == null) {
            result.put(ATTRIBUTE_GENDER, VALUE_ATTRIBUTE_NULL);

        } else if (!validateGender(gender)) {
            result.put(ATTRIBUTE_GENDER, VALUE_ATTRIBUTE_GENDER_INCORRECT);
        }

        return result;
    }

    public static Map<String, String> validateUpdateProfile(String login, String email, String surname,
                                                            String firstName, String fatherName, String passportId,
                                                            String birth, String gender) throws ServiceException {
        Map<String, String> result = new HashMap<>();

        if (login == null) {
            result.put(Attribute.ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_NULL);
        } else if (login.length() > 40) {
            result.put(Attribute.ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_LONG_LENGTH);
        }


        if (email == null) {
            result.put(Attribute.ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_NULL);
        } else if (email.length() > MAX_LENGTH_FIELD) {
            result.put(Attribute.ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_LONG_LENGTH);
        } else if (!validateEmailRegex(email)) {
            result.put(Attribute.ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_EMAIL_INCORRECT);
        }

        if (birth == null) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_NULL);
        } else if (birth.length() > MAX_LENGTH_FIELD) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_LONG_LENGTH);
        } else if (!validateCorrectStringBirth(birth)) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_BIRTH_INCORRECT);
        } else if (!validateBirthInPast(birth)) {
            result.put(ATTRIBUTE_BIRTH, VALUE_ATTRIBUTE_BIRTH_IN_FUTURE);
        }

        if (surname == null) {
            result.put(ATTRIBUTE_SURNAME, VALUE_ATTRIBUTE_NULL);
        } else if (surname.length() > 25) {
            result.put(ATTRIBUTE_SURNAME, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (firstName == null) {
            result.put(ATTRIBUTE_FIRST_NAME, VALUE_ATTRIBUTE_NULL);
        } else if (firstName.length() > 25) {
            result.put(ATTRIBUTE_FIRST_NAME, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (fatherName == null) {
            result.put(ATTRIBUTE_FATHER_NAME, VALUE_ATTRIBUTE_NULL);
        } else if (fatherName.length() > 25) {
            result.put(ATTRIBUTE_FATHER_NAME, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (passportId == null) {
            result.put(Attribute.ATTRIBUTE_PASSPORT_ID, VALUE_ATTRIBUTE_NULL);
        } else if (passportId.length() > 25) {
            result.put(Attribute.ATTRIBUTE_PASSPORT_ID, VALUE_ATTRIBUTE_LONG_LENGTH);
        }

        if (gender == null) {
            result.put(ATTRIBUTE_GENDER, VALUE_ATTRIBUTE_NULL);
        } else if (!validateGender(gender)) {
            result.put(ATTRIBUTE_GENDER, VALUE_ATTRIBUTE_GENDER_INCORRECT);
        }

        return result;
    }

    public static Map<String, String> validateExtensionPhoto(String namePhoto) {
        Map<String, String> result = new HashMap<>();
        if (!validateFileExtensionPhoto(namePhoto)) {
            result.put(ATTRIBUTE_PHOTO, VALUE_ATTRIBUTE_PHOTO_INCORRECT_FORMAT);
        }
        return result;
    }

    public static Map<String, String> validateLogin(String login) {

        Map<String, String> results = new HashMap<>();

        if (login == null) {
            results.put(Attribute.ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_NULL);
        } else if (login.length() > 25) {
            results.put(Attribute.ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_LONG_LENGTH);
        }
        return results;
    }

    public static Map<String, String> validatePassword(String password) {
        Map<String, String> resultsMap = new HashMap<>();

        if (password == null) {
            resultsMap.put(ATTRIBUTE_PASSWORD, VALUE_ATTRIBUTE_NULL);
        } else if (password.length() > MAX_LENGTH_FIELD) {
            resultsMap.put(ATTRIBUTE_PASSWORD, VALUE_ATTRIBUTE_LONG_LENGTH);
        } else if (!validateComplexPassword(password)) {
            resultsMap.put(ATTRIBUTE_PASSWORD, VALUE_ATTRIBUTE_SIMPLE_PASSWORD);
        }
        return resultsMap;
    }


    private static boolean validateComplexPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    private static boolean validateEmailRegex(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    private static boolean validateCorrectStringBirth(String birth) {
        try {
            LocalDate.parse(birth);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    private static boolean validateBirthInPast(String birth) {

        LocalDate parseBirth;
        try {
            parseBirth = LocalDate.parse(birth);
        } catch (DateTimeParseException e) {
            return false;
        }
        return LocalDate.now().isAfter(parseBirth);
    }

    private static boolean validateFileExtensionPhoto(String name) {
        if (!name.isEmpty()) {
            String extension = name.substring(name.lastIndexOf('.'));
            return ATTRIBUTE_JPG.equalsIgnoreCase(extension) || ATTRIBUTE_JPEG.equalsIgnoreCase(extension)
                    || ATTRIBUTE_PNG.equalsIgnoreCase(extension);
        }
        return false;
    }

    private static boolean validateGender(String gender) {
        if (gender != null) {
            return ATTRIBUTE_MALE.equalsIgnoreCase(gender) || ATTRIBUTE_FEMALE.equalsIgnoreCase(gender);
        }
        return false;
    }
}
