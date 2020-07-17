package by.mishota.graduation.entity;

public class FacultyPriority {
    private int id;
    private int entrantId;
    private int facultyId;
    private int priority;

    public FacultyPriority(int id, int entrantId, int facultyId, int priority) {
        this.id = id;
        this.entrantId = entrantId;
        this.facultyId = facultyId;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(int entrantId) {
        this.entrantId = entrantId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FacultyPriority that = (FacultyPriority) o;

        if (id != that.id) return false;
        if (entrantId != that.entrantId) return false;
        if (facultyId != that.facultyId) return false;
        return priority == that.priority;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + entrantId;
        result = 31 * result + facultyId;
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("FacultyPriority(")
                .append(id).append(", ")
                .append(entrantId).append(", ")
                .append(facultyId).append(", ")
                .append(priority).append(") ");

        return builder.toString();
    }
}
