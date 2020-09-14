package by.mishota.graduation.dao.sql_query;

public class SqlQueryPhotosDao {
    public static final String SELECT_ALL="select id,name from photos order by id";
    public static final String SELECT_BY_ID="select id,name from photos where id=";
    public static final String SELECT_ALL_BY_USER_ID="select id,name from photos where user_id=";
    public static final String SELECT_BY_NAME="select id,name from photos where name=?";

    public static final String INSERT="insert into photos (user_id, name) values (?,?);";

    public static final String UPDATE_BY_ID="update photos set name = ? where id=?;";

    public static final String DELETE="delete from photos where name=?";

    private SqlQueryPhotosDao() {
    }
}
