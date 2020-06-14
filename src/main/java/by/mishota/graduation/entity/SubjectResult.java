package by.mishota.graduation.entity;

public class SubjectResult {

    private int id;
    private Subject subject;
    private int result;

    public SubjectResult(Subject subject, int result) {
        this.subject = subject;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectResult that = (SubjectResult) o;

        if (id != that.id) return false;
        if (result != that.result) return false;
        return subject != null ? subject.equals(that.subject) : that.subject == null;
    }

    @Override
    public int hashCode() {
        int result1 = id;
        result1 = 31 * result1 + (subject != null ? subject.hashCode() : 0);
        result1 = 31 * result1 + result;
        return result1;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("SubjectResult( ").append(id).append(", ").append(subject).append(",  ")
                .append("result= ").append(result).append(")");
        return builder.toString();
    }
}
