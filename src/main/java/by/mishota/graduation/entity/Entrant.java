package by.mishota.graduation.entity;

import java.util.List;

public class Entrant {

    private int id;
    private User user;
    private int certificate;
    private List<SubjectResult> results;
    private List<FacultyPriority> priorities;

    public Entrant(User user, int certificate, List<SubjectResult> results, List<FacultyPriority> priorities) {
        this.user = user;
        this.certificate = certificate;
        this.results = results;
        this.priorities = priorities;
    }

    public Entrant() {
    }

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

    public int getCertificate() {
        return certificate;
    }

    public void setCertificate(int certificate) {
        this.certificate = certificate;
    }

    public List<SubjectResult> getResults() {
        return results;
    }

    public void setResults(List<SubjectResult> results) {
        this.results = results;
    }

    public List<FacultyPriority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<FacultyPriority> priorities) {
        this.priorities = priorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entrant entrant = (Entrant) o;

        if (id != entrant.id) return false;
        if (certificate != entrant.certificate) return false;
        if (user != null ? !user.equals(entrant.user) : entrant.user != null) return false;
        if (results != null ? !results.equals(entrant.results) : entrant.results != null) return false;
        return priorities != null ? priorities.equals(entrant.priorities) : entrant.priorities == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + certificate;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + (priorities != null ? priorities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        return new StringBuilder().append("Entrant( ").
                append(id).append(", ")
                .append(user).append(", certificate= ")
                .append(certificate).append(", ")
                .append(results).append(", ")
                .append(priorities).append(") ").toString();
    }
}
