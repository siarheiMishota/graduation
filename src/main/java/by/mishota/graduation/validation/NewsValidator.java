package by.mishota.graduation.validation;

import java.util.HashMap;
import java.util.Map;

import static by.mishota.graduation.controller.Attribute.*;

public class NewsValidator {
    private static final int maxLengthName = 200;
    private static final int maxLengthBriefDescription = 2000;
    private static final int maxLengthEnglishVariable = 20000;
    private static final int maxLengthRussianVariable = 20000;

    private NewsValidator() {
    }

    public static Map<String, String> validateNews(String nameRu,String nameEn, String briefDescriptionRu,String briefDescriptionEn, String englishVariable, String russianVariable) {
        Map<String, String> errors = new HashMap<>();

        if (nameRu == null || nameRu.isEmpty()) {
            errors.put(ATTRIBUTE_NEWS_NAME_RU, VALUE_ATTRIBUTE_EMPTY);
        } else if (nameRu.length() > maxLengthName) {
            errors.put(ATTRIBUTE_NEWS_NAME_RU, VALUE_ATTRIBUTE_LONG);
        }

        if (nameEn == null || nameEn.isEmpty()) {
            errors.put(ATTRIBUTE_NEWS_NAME_EN, VALUE_ATTRIBUTE_EMPTY);
        } else if (nameEn.length() > maxLengthName) {
            errors.put(ATTRIBUTE_NEWS_NAME_EN, VALUE_ATTRIBUTE_LONG);
        }

        if (briefDescriptionRu == null || briefDescriptionRu.isEmpty()) {
            errors.put(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_RU, VALUE_ATTRIBUTE_EMPTY);
        } else if (briefDescriptionRu.length() > maxLengthBriefDescription) {
            errors.put(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_RU, VALUE_ATTRIBUTE_LONG);
        }

        if (briefDescriptionEn == null || briefDescriptionEn.isEmpty()) {
            errors.put(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_EN, VALUE_ATTRIBUTE_EMPTY);
        } else if (briefDescriptionEn.length() > maxLengthBriefDescription) {
            errors.put(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_EN, VALUE_ATTRIBUTE_LONG);
        }

        if (englishVariable == null || englishVariable.isEmpty()) {
            errors.put(ATTRIBUTE_NEWS_ENGLISH_VARIABLE, VALUE_ATTRIBUTE_EMPTY);
        } else if (englishVariable.length() > maxLengthEnglishVariable) {
            errors.put(ATTRIBUTE_NEWS_ENGLISH_VARIABLE, VALUE_ATTRIBUTE_LONG);
        }

        if (russianVariable == null || russianVariable.isEmpty()) {
            errors.put(ATTRIBUTE_NEWS_RUSSIAN_VARIABLE, VALUE_ATTRIBUTE_EMPTY);
        } else if (russianVariable.length() > maxLengthRussianVariable) {
            errors.put(ATTRIBUTE_NEWS_RUSSIAN_VARIABLE, VALUE_ATTRIBUTE_LONG);
        }
        return errors;
    }
}
