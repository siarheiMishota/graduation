package by.mishota.graduation.validation;

import java.util.HashMap;
import java.util.Map;

import static by.mishota.graduation.controller.Attribute.*;

public class EnterCertificatesValidator {


    public static Map<String, String> validateMark(String markString) {
        Map<String, String> errors = new HashMap<>();

        try {
            Integer.parseInt(markString);
        } catch (NumberFormatException e) {
            errors.put(ATTRIBUTE_MARK, VALUE_ATTRIBUTE_NOT_NUMBER);
            return errors;
        }

        int mark = Integer.parseInt(markString);

        if (mark < 0 || mark > 100) {
            errors.put(ATTRIBUTE_MARK, VALUE_ATTRIBUTE_NOT_IN_RANGE);
        }
        return errors;

    }
}
