package by.mishota.graduation.validation;

public class UtilValidator {

    public static boolean positiveNumberValidate(String numberPage) {
        if (numberPage == null) {
            return false;
        }

        try {
            int number = Integer.parseInt(numberPage);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
