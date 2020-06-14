package by.mishota.graduation.entity;

import java.nio.file.Path;
import java.time.LocalDate;

public class User {
    private int id;
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

    private User(int id, int passportId, LocalDate birth, String login, String password, String email, String firstName,
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Path getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(Path pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (passportId != user.passportId) return false;
        if (confirmed != user.confirmed) return false;
        if (!birth.equals(user.birth)) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (!email.equals(user.email)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!surname.equals(user.surname)) return false;
        if (!fatherName.equals(user.fatherName)) return false;
        if (gender != user.gender) return false;
        return pathToPhoto.equals(user.pathToPhoto);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + passportId;
        result = 31 * result + birth.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + fatherName.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + (confirmed ? 1 : 0);
        result = 31 * result + pathToPhoto.hashCode();
        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("User{ ");
        builder.append(id).append(", ");
        builder.append(passportId).append(", ");
        builder.append(birth).append(", ");
        builder.append(login).append(", ");
        builder.append(password).append(", ");
        builder.append(email).append(", ");
        builder.append(firstName).append(", ");
        builder.append(surname).append(", ");
        builder.append(fatherName).append(", ");
        builder.append(gender).append(", ");
        builder.append(confirmed);

        return builder.toString();
    }

    public static class Builder {
        private int id;
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

        public Builder() {
            id = 0;
            passportId = 0;
            birth = LocalDate.now();
            login = "";
            password = "";
            email = "";
            firstName = "";
            surname = "";
            fatherName = "";
            gender = Gender.MALE;
            confirmed = false;
            pathToPhoto = Path.of("");

        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPassportId(int passportId) {
            this.passportId = passportId;
            return this;
        }

        public Builder setBirth(LocalDate birth) {
            this.birth = birth;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setFatherName(String fatherName) {
            this.fatherName = fatherName;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder setConfirmed(boolean confirmed) {
            this.confirmed = confirmed;
            return this;
        }

        public Builder setPathToPhoto(Path pathToPhoto) {
            this.pathToPhoto = pathToPhoto;
            return this;
        }

        public User build() {
            return new User(id, passportId, birth, login, password, email, firstName, surname, fatherName, gender, confirmed, pathToPhoto);
        }
    }
}