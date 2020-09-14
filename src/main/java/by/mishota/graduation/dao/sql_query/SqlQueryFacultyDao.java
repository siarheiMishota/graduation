package by.mishota.graduation.dao.sql_query;

public class SqlQueryFacultyDao {

    public static final String SELECT_ALL = "select id, name, number_pay_places, number_free_places from faculties";
    public static final String SELECT_BY_NAME = "select id, name, number_pay_places, number_free_places from faculties where name='";
    public static final String SELECT_BY_ID = "select id, name, number_pay_places, number_free_places from faculties where id=";
    public static final String SELECT_WHERE_FREE_PLACES_MORE_SPECIFY = "select id, name, number_pay_places, number_free_places from faculties where number_free_places>";
    public static final String SELECT_WHERE_FREE_PLACES_LESS_SPECIFY = "select id, name, number_pay_places, number_free_places from faculties where number_free_places<";
    public static final String SELECT_NUMBER_ALL_FACULTIES="select count(id) from faculties";
    public static final String SELECT_FACULTY_IS_EXIST="select count(id) from faculties where id=";
    public static final String SELECT_FACULTIES_OFFSET_FEW_SKIPPING_FEW_="select id, name, number_pay_places, number_free_places from faculties order BY  id limit ?,?";


    public static final String UPDATE = "update faculties set name =? ,number_free_places=?,number_pay_places=? where id =?;";

    public static final String INSERT="insert into faculties (name, number_free_places, number_pay_places) values (?,?,?);";
    public static final String DELETE ="delete from faculties where id=";
    public static final String DELETE_BY_NAME="delete from faculties where name=?";

    private SqlQueryFacultyDao() {
    }
}
