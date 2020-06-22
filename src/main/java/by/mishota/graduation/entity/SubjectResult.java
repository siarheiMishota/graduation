package by.mishota.graduation.entity;

public class SubjectResult {

    private int id;
    private int subject;
    private int result;

    public SubjectResult(int subject, int result) {
        this.subject = subject;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
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
        if (subject != that.subject) return false;
        return result == that.result;
    }

    @Override
    public int hashCode() {
        int result1 = id;
        result1 = 31 * result1 + subject;
        result1 = 31 * result1 + result;
        return result1;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("SubjectResult( ").append(id).append(", id subject= ").append(subject).append(",  ")
                .append("result= ").append(result).append(")");
        return builder.toString();
    }
}
