package by.mishota.graduation.dao.sql_query;

public class SqlQueryEntrantDao {

    public static final String INSERT="insert into entrants (user_id, certificate) values (?,?);";

    public static final String UPDATE="update entrants set user_id =? ,certificate=?, date=? where id=?";

    public static final String DELETE="delete from entrants where id=";

    public static final String SELECT_FIND_BY_ID="select id, user_id, certificate, date from entrants where id=";
    public static final String SELECT_FIND_BY_USER_ID="select id, user_id, certificate, date from entrants where user_id=";
    public static final String SELECT_FIND_ALL="select id, user_id, certificate, date from entrants where year(date)=year(now());";
    public static final String SELECT_OFFSET_FEW_SKIPPING_FEW ="select id, user_id, certificate, date from entrants order by id limit ?,?";
    public static final String SELECT_NUMBER_ALL_ENTRANTS="select count(id) from entrants";


    private SqlQueryEntrantDao() {}
}
