package by.mishota.graduation.entity;

public class SubjectResult {

    private int id;
    private int subjectId;
    private int points;

    public SubjectResult(int subjectId, int points) {
        this.subjectId = subjectId;
        this.points = points;
    }

    public SubjectResult() {
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
        int result1 = id;
        result1 = 31 * result1 + subjectId;
        result1 = 31 * result1 + points;
        return result1;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("SubjectResult( ").append(id).append(", id subject= ").append(subjectId).append(",  ")
                .append("result= ").append(points).append(")");
        return builder.toString();
    }
}
