package by.mishota.graduation.entity;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Objects;

public class User {
    private final int id;
    private int passportId;
    private LocalDate birth;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String surname;
    private String fatherName;
    private Gender gender;
    private boolean confirmed;
    private Path pathToPhoto;

    public User(int id, int passportId, LocalDate birth, String login, String password, String email, String firstName,
                String surname, String fatherName, Gender gender, boolean confirmed, Path pathToPhoto) {
        this.id = id;
        this.passportId = passportId;
        this.birth = birth;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.fatherName = fatherName;
        this.gender = gender;
        this.confirmed = confirmed;
        this.pathToPhoto = pathToPhoto;
    }

    public static User create(int id, int passportId, LocalDate birth, String login, String password, String email,
                              String firstName, String surname, String fatherName, Gender gender, boolean confirmed,
                              Path pathToPhoto) {
        return new User(id, passportId, birth, login, password, email, firstName, surname, fatherName, gender,
                confirmed, pathToPhoto);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                passportId == user.passportId &&
                confirmed == user.confirmed &&
                Objects.equals(birth, user.birth) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(fatherName, user.fatherName) &&
                gender == user.gender &&
                Objects.equals(pathToPhoto, user.pathToPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passportId, birth, login, password, email, firstName, surname, fatherName, gender, confirmed, pathToPhoto);
    }

    @Override
    public String toString() {
        return "User{ " + id + ", " + passportId + ", " + birth + ", " + login + ", " + password + ", " + email + ", " +
                firstName + ", " + surname + ", " + fatherName + ", " + gender + ", " + confirmed;
    }
}