package by.mishota.graduation.entity;

public enum Role {
    GUEST, USER, ADMIN;

    public static Role valueOfIgnoreCase(String name) {

        return valueOf(name.toUpperCase());
    }
}
