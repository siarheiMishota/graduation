package by.mishota.graduation.dao.impl;

public class SqlColumnName {

    public static final String USER_ID = "id";
    public static final String USER_PASSPORT_ID = "passport_id";
    public static final String USER_DATE_BIRTH = "date_birth";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_EMAIL = "email";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_FATHER_NAME = "father_name";
    public static final String USER_GENDER = "gender";
    public static final String USER_CONFIRMED = "confirmed";
    public static final String USER_PHOTO = "photo";
    public static final String USER_ROLE = "role";
    public static final String USER_ACTIVATION_CODE = "activation_code";

    public static final String USER_PHOTO_NAME = "name";
    public static final String USER_PHOTO_ID= "id";

    public static final String SUBJECT_ID = "id";
    public static final String SUBJECT_NAME = "name";

    public static final String FACULTY_ID = "id";
    public static final String FACULTY_NAME = "name";
    public static final String FACULTY_NUMBER_FREE_PLACES = "number_free_places";
    public static final String FACULTY_NUMBER_PAY_PLACES = "number_pay_places";

    public static final String STUDENT_ID = "id";
    public static final String STUDENT_BUDGET = "budget";
    public static final String STUDENT_FACULTY_ID = "faculty_id";
    public static final String STUDENT_USER_ID = "user_id";

    public static final String ENTRANT_ID = "id";
    public static final String ENTRANT_USER_ID = "user_id";
    public static final String ENTRANT_CERTIFICATE = "certificate";
    public static final String ENTRANT_DATE = "date";

    public static final String SUBJECT_RESULT_ID = "id";
    public static final String SUBJECT_RESULT_ENTRANT_ID = "entrant_id";
    public static final String SUBJECT_RESULT_SUBJECT_ID = "subject_id";
    public static final String SUBJECT_RESULT_POINTS = "points";

    public static final String FACULTY_PRIORITY_ID="id";
    public static final String FACULTY_PRIORITY_ENTRANT_ID="entrant_id";
    public static final String FACULTY_PRIORITY_FACULTY_ID="faculty_id";
    public static final String FACULTY_PRIORITY_PRIORITY="priority";

    public static final String NEWS_ID="id";
    public static final String NEWS_NAME_RU="name_ru";
    public static final String NEWS_NAME_EN="name_en";
    public static final String NEWS_USER_CREATOR_ID="user_creator_id";
    public static final String NEWS_BRIEF_DESCRIPTION_RU="brief_description_ru";
    public static final String NEWS_BRIEF_DESCRIPTION_EN="brief_description_en";
    public static final String NEWS_ENGLISH_VARIABLE="english_variable";
    public static final String NEWS_RUSSIAN_VARIABLE="russian_variable";
    public static final String NEWS_CREATION_DATE="creation_date";


    private SqlColumnName() {
    }

}
