package by.mishota.graduation.entity;

import java.util.List;
import java.util.Map;

public class Entrant {

    private int id;
    private User user;
    private int certificate;
    private Map<Integer,Integer> results;
    private Map<Integer, Integer> priority;

    public Entrant(User user, int certificate, Map<Integer,Integer> results, Map<Integer, Integer> priority) {
        this.user = user;
        this.certificate = certificate;
        this.results = results;
        this.priority = priority;
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

    public Map<Integer, Integer> getResults() {
        return results;
    }

    public void setResults(Map<Integer,Integer> results) {
        this.results = results;
    }

    public Map<Integer, Integer> getPriority() {
        return priority;
    }

    public void setPriority(Map<Integer, Integer> priority) {
        this.priority = priority;
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
        return priority != null ? priority.equals(entrant.priority) : entrant.priority == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + certificate;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("Entrant( ").append(id).append(", ").append(user).append(", cerificate= ").append(certificate)
                .append(", ").append(results).append(", ").append(priority).append(")");
        return builder.toString();
    }
}
