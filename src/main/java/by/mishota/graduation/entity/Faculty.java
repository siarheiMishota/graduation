package by.mishota.graduation.entity;

import java.util.List;

public class Faculty {

    private int id;
    private String name;
    private int numberFreePlaces;
    private int numberPayPlaces;
    private List<Integer> idStudents;
    private List<Integer> idNeedSubjects;

    public Faculty() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberFreePlaces() {
        return numberFreePlaces;
    }

    public void setNumberFreePlaces(int numberFreePlaces) {
        this.numberFreePlaces = numberFreePlaces;
    }

    public int getNumberPayPlaces() {
        return numberPayPlaces;
    }

    public void setNumberPayPlaces(int numberPayPlaces) {
        this.numberPayPlaces = numberPayPlaces;
    }

    public List<Integer> getIdStudents() {
        return idStudents;
    }

    public void setIdStudents(List<Integer> idStudents) {
        this.idStudents = idStudents;
    }

    public List<Integer> getIdNeedSubjects() {
        return idNeedSubjects;
    }

    public void setIdNeedSubjects(List<Integer> idNeedSubjects) {
        this.idNeedSubjects = idNeedSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        if (id != faculty.id) return false;
        if (numberFreePlaces != faculty.numberFreePlaces) return false;
        if (numberPayPlaces != faculty.numberPayPlaces) return false;
        if (name != null ? !name.equals(faculty.name) : faculty.name != null) return false;
        if (idStudents != null ? !idStudents.equals(faculty.idStudents) : faculty.idStudents != null) return false;
        return idNeedSubjects != null ? idNeedSubjects.equals(faculty.idNeedSubjects) : faculty.idNeedSubjects == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + numberFreePlaces;
        result = 31 * result + numberPayPlaces;
        result = 31 * result + (idStudents != null ? idStudents.hashCode() : 0);
        result = 31 * result + (idNeedSubjects != null ? idNeedSubjects.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("Faculty( ").append(id).append(", ").append(name).append(", free places= ")
                .append(numberFreePlaces).append(", pay places= ").append(numberPayPlaces).append(", ")
                .append(idNeedSubjects).append(", ").append(idStudents).append(")");

        return builder.toString();
    }
}
