package by.mishota.graduation.dao.sql_query;

public class SqlQueryUserDao {

   public static final String SELECT_ALL_USERS = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users";
   public static final String SELECT_FIND_BY_ID = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where id=";
   public static final String SELECT_FIND_BY_LOGIN = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where login=";
   public static final String SELECT_FIND_BY_ACTIVATION_CODE = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where activation_code=";
   public static final String SELECT_FIND_BY_EMAIL = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where email=";
   public static final String SELECT_ALL_MALE = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where gender='male'";
   public static final String SELECT_ALL_FEMALES = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where gender='female'";
   public static final String SELECT_ALL_ADULT = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where date_birth  <DATE_ADD(CURDATE(), INTERVAL -18 YEAR )"; //TODO
   public static final String SELECT_ALL_ADULTS = "SELECT id, passport_id, date_birth, login, password, email, first_name, surname, father_name, gender, confirmed, role, activation_code FROM users where date_birth > NOW()";

   public static final String UPDATE_USER_BY_ID="update users" +
           "set passport_id =?, date_birth=?, login=?,password=?, email=?, first_name=?, surname=?, father_name=?, gender=?, confirmed=?, role=?, activation_code=? \n" +
           "where id=?;";

   public static final String INSERT_NEW_USER = "INSERT INTO users( passport_id, date_birth, login, password, email," +
           " first_name, surname, father_name, gender, confirmed,activation_code) value(?,?,?,?,?,?,?,?,?,?,?)";

   public static final String SELECT_FIND_COUNT_BY_PASSPORT_ID = "select count(id) as count from users where passport_id='";
   public static final String SELECT_FIND_COUNT_BY_LOGIN = "select count(id) as count from users where login='";
   public static final String SELECT_FIND_COUNT_BY_EMAIL = "select count(id) as count from users where email='";

    private SqlQueryUserDao(){
    }
}
