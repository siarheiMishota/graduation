package by.mishota.graduation.dao.sql_query;

public class SqlQuerySubjectResultDao {

    public static final String SELECT_ALL = "select id, entrant_id, subject_id, points  from subjects_results";
    public static final String SELECT_BY_ID = "select id, entrant_id, subject_id, points  from subjects_results where id=";
    public static final String SELECT_ALL_BY_ENTRANT_ID = "select id, entrant_id, subject_id, points  from subjects_results where entrant_id=";
    public static final String SELECT_ALL_BY_SUBJECT_ID = "select id, entrant_id, subject_id, points  from subjects_results where subject_id=";

    public static final String INSERT="insert into subjects_results (entrant_id, subject_id, points) values (?,?,?);";

    public static final String DELETE="delete from subjects_results where id=";

    public static final String UPDATE_SUBJECT_RESULT_BY_ID="update subjects_results set entrant_id=?, subject_id=?,points=? where id=?";


    private SqlQuerySubjectResultDao() {
    }
}
