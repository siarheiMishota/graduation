package by.mishota.graduation.dao.sql_query;

public class SqlQuerySubjectDao {
    public static final String SELECT_FIND_BY_ID = "SELECT id, name FROM subjects where id=";
    public static final String SELECT_ALL = "SELECT id, name FROM subjects";
    public static final String SELECT_FIND_ALL_BY_FACULTY = "select subjects.id, name, faculties_id, subject_id from subjects join subjects_to_faculties stf on subjects.id = stf.subject_id where faculties_id=";

    public static final String ADD = "INSERT INTO subjects(name) value (?);";

    public static final String UPDATE = "update subjects set  name=? where id=?";

    public static final String DELETE="delete from subjects where id=";

    private SqlQuerySubjectDao() {
    }
}
