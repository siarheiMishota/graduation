package by.mishota.graduation.validation;

public class AdminChecking {
    private final static String ADMIN_LOGIN="admin";
    private final static String ADMIN_PASSWORD="admin";

    public static boolean check(String enterLogin,String enterPass){
        return ADMIN_LOGIN.equals(enterLogin)&&ADMIN_PASSWORD.equals(enterPass);
    }
}
