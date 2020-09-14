package by.mishota.graduation.entity;

public class SubjectResult {

    private int id;
    private int subjectId;
    private int points;

    public SubjectResult(int subjectId, int points) {
        this.subjectId = subjectId;
        this.points = points;
        this.id=-1;
    }

    public SubjectResult() {
        this.id=-1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubjectResult that = (SubjectResult) o;

        if (id != that.id) return false;
        if (subjectId != that.subjectId) return false;
        return points == that.points;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + subjectId;
        result = 31 * result + points;
        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("SubjectResult( id= ").append(id).append(", subject= ").append(subjectId).append(",  ")
                .append("result= ").append(points).append(")");
        return builder.toString();
    }
}
