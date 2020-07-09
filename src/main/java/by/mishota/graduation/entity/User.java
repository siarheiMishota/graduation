package by.mishota.graduation.entity;

import java.time.LocalDate;

public class User {
    private int id;
    private String passportId;
    private LocalDate birth;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String surname;
    private String fatherName;
    private Gender gender;
    private boolean confirmed;
    private String photo;
    private Role role;
    private String activationCode;

    private User(int id, String passportId, LocalDate birth, String login, String password, String email, String firstName,
                 String surname, String fatherName, Gender gender, boolean confirmed, String photo, Role role, String activationCode) {
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
        this.photo = photo;
        this.role = role;
        this.activationCode = activationCode;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (confirmed != user.confirmed) return false;
        if (passportId != null ? !passportId.equals(user.passportId) : user.passportId != null) return false;
        if (birth != null ? !birth.equals(user.birth) : user.birth != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (fatherName != null ? !fatherName.equals(user.fatherName) : user.fatherName != null) return false;
        if (gender != user.gender) return false;
        return photo != null ? photo.equals(user.photo) : user.photo == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (passportId != null ? passportId.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (fatherName != null ? fatherName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (confirmed ? 1 : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
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
        builder.append(photo).append(", ");
        builder.append(confirmed);

        return builder.toString();
    }

    public static class Builder {
        private int id;
        private String passportId;
        private LocalDate birth;
        private String login;
        private String password;
        private String email;
        private String firstName;
        private String surname;
        private String fatherName;
        private Gender gender;
        private boolean confirmed;
        private String pathToPhoto;
        private Role role;
        private String activationCode;

        public Builder() {
            id = 0;
            passportId = "";
            birth = LocalDate.now();
            login = "";
            password = "";
            email = "";
            firstName = "";
            surname = "";
            fatherName = "";
            gender = Gender.MALE;
            confirmed = false;
            pathToPhoto = "";
            role = Role.USER;
            activationCode = null;

        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPassportId(String passportId) {
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

        public Builder setPathToPhoto(String pathToPhoto) {
            this.pathToPhoto = pathToPhoto;
            return this;
        }

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setActivationCode(String activationCode) {
            this.activationCode = activationCode;
            return this;
        }

        public User build() {
            return new User(id, passportId, birth, login, password, email, firstName, surname, fatherName, gender, confirmed, pathToPhoto, role, activationCode);
        }
    }


}