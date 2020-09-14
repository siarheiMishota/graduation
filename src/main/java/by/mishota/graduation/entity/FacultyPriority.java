package by.mishota.graduation.entity;

public class FacultyPriority {
    private int id;
    private int facultyId;
    private int priority;

    public FacultyPriority( int facultyId, int priority) {
        this.facultyId = facultyId;
        this.priority = priority;
    }

    public FacultyPriority() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        if (facultyId != that.facultyId) return false;
        return priority == that.priority;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + facultyId;
        result = 31 * result + priority;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("FacultyPriority( id= ")
                .append(id).append(", facultyId= ")
                .append(facultyId).append(", priority= ")
                .append(priority).append(") ");

        return builder.toString();
    }
}
