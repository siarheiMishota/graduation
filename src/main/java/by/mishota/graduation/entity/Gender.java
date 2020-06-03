package by.mishota.graduation.entity;

public enum Gender {
    MALE, FEMALE;

    public static Gender valueOfIgnoreCase(String name) {

        return valueOf(name.toUpperCase());
    }
}