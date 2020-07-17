package by.mishota.graduation.dao.sql_query;

public class SqlQueryFacultyDao {

    public static final String SELECT_ALL = "select id, name, number_pay_places, number_free_places from faculties";
    public static final String SELECT_BY_ID = "select id, name, number_pay_places, number_free_places from faculties where id=";
    public static final String SELECT_WHERE_FREE_PLACES_MORE_SPECIFY = "select id, name, number_pay_places, number_free_places from faculties where number_free_places>";
    public static final String SELECT_WHERE_FREE_PLACES_LESS_SPECIFY = "select id, name, number_pay_places, number_free_places from faculties where number_free_places<";

    public static final String UPDATE = "update faculties set name =? ,number_free_places=?,number_pay_places=? where id =?;";

    public static final String INSERT="insert into faculties (name, number_free_places, number_pay_places) values (?,?,?);";

    private SqlQueryFacultyDao() {
    }
}
