package by.mishota.graduation.entity;

public class Student {
    private int id;
    private User user;
//    private Faculty faculty;
    private boolean budget;

    public Student(User user,Faculty faculty, boolean budget) {
        this.user = user;
//        this.faculty=faculty;
        this.budget = budget;
    }

    public Student() {
    }

//    public Faculty getFaculty() {
//        return faculty;
//    }
//
//    public void setFaculty(Faculty faculty) {
//        this.faculty = faculty;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isBudget() {
        return budget;
    }

    public void setBudget(boolean budget) {
        this.budget = budget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (budget != student.budget) return false;
        return user != null ? user.equals(student.user) : student.user == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (budget ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Student( ").append(id).append(", budget- ").append(budget).append(",  ")
                .append(user).append(")");
        return builder.toString();
    }
}
