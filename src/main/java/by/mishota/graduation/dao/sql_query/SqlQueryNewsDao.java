package by.mishota.graduation.dao.sql_query;

public class SqlQueryNewsDao {
    public static final String INSERT = "insert into news (name_ru, name_en, user_creator_id, brief_description_ru, brief_description_en, english_variable, russian_variable, creation_date) value(?,?,?,?,?,?,?,?);";
    public static final String UPDATE = "update news set name_ru=?,name_en=?,user_creator_id=?,brief_description_ru=?,brief_description_en=?,english_variable=?,russian_variable=?,creation_date=? where id=?;";
    public static final String DELETE = "delete from news where id=?;";

    public static final String SELECT_FIND_ALL = "select id, name_ru,name_en, user_creator_id, brief_description_ru,brief_description_en, english_variable, russian_variable, creation_date from news";
    public static final String SELECT_FIND_BY_ID = "select id, name_ru,name_en, user_creator_id, brief_description_ru,brief_description_en, english_variable, russian_variable, creation_date from news where id=";
    public static final String SELECT_OFFSET_FES_SKIPPING_FEW = "select id, name_ru,name_en, user_creator_id, brief_description_ru,brief_description_en, english_variable, russian_variable, creation_date from news order by id limit ?,?;";
    public static final String SELECT_NUMBER_ALL_NEWS="select count(id) from news";

}
