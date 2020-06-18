package by.mishota.graduation.entity;

public enum Role {
    USER,ADMIN;

    public static Role valueOfIgnoreCase(String name) {

        return valueOf(name.toUpperCase());
    }
}
