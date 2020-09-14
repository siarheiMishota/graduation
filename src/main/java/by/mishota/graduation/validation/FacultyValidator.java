package by.mishota.graduation.validation;

import java.util.List;

public class FacultyValidator {

    public static boolean validateFacultyId(String facultyIdString) {
        if (facultyIdString == null) {
            return false;
        }
        try {
            return Integer.parseInt(facultyIdString) >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateFacultiesId(List<String> facultiesIdString){
        for (String facultyIdString:facultiesIdString) {
            if (!validateFacultyId(facultyIdString)){
                return false;
            }
        }
        return true;
    }

    private FacultyValidator() {
    }
}
