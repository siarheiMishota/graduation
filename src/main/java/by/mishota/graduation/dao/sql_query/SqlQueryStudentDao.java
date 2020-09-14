package by.mishota.graduation.dao.sql_query;

public class SqlQueryStudentDao {
    public static final String SELECT_ALL = "SELECT id,user_id, faculty_id, budget FROM students ";
    public static final String SELECT_ALL_MALE = "select students.id,user_id, faculty_id, budget from students join users u on students.user_id = u.id where gender='male'";
    public static final String SELECT_ALL_FEMALE = "select students.id,user_id, faculty_id, budget from students join users u on students.user_id = u.id where gender='female'";
    public static final String SELECT_ALL_PAYER = "select id,user_id, faculty_id, budget from students where budget=false";
    public static final String SELECT_ALL_FREER = "select id,user_id, faculty_id, budget from students where budget=true";
    public static final String SELECT_BY_ID = "SELECT id,user_id, faculty_id, budget FROM students where id=";
    public static final String SELECT_ALL_BY_FACULTY_ID = "select id,user_id, faculty_id, budget from students where faculty_id=";

    public static final String UPDATE = "update students set user_id=?, faculty_id =?,budget=? where id=?";

    public static final String INSERT="insert into students (user_id, faculty_id, budget) value (?,?,?);";

    public static final String DELETE="delete from students where id=";

    private SqlQueryStudentDao() {
    }
}
