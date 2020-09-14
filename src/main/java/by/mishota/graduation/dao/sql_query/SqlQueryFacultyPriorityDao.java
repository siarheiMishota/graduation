package by.mishota.graduation.dao.sql_query;

public class SqlQueryFacultyPriorityDao {

    public static final String INSERT = "insert into entrants_faculties_priority (entrant_id, faculty_id, priority)" +
            "values (?,?,?);";

    public static final String UPDATE = "update entrants_faculties_priority set faculty_id=?,priority=? where id=?;";
    public static final String DELETE = "delete FROM entrants_faculties_priority where id=";
    public static final String DELETE_ALL_BY_ENTRANT = "delete FROM entrants_faculties_priority where entrant_id=";

    public static final String SELECT_FIND_ALL="select id, entrant_id, faculty_id, priority from entrants_faculties_priority ";
    public static final String SELECT_FIND_BY_ID="select id, entrant_id, faculty_id, priority from entrants_faculties_priority where  id=";
    public static final String SELECT_FIND_ALL_BY_ENTRANT_ID ="select id, entrant_id, faculty_id, priority from entrants_faculties_priority where  entrant_id=";
    public static final String SELECT_FIND_ALL_BY_FACULTY_ID ="select id, entrant_id, faculty_id, priority from entrants_faculties_priority where  faculty_id=";

    private SqlQueryFacultyPriorityDao() {
    }
}
