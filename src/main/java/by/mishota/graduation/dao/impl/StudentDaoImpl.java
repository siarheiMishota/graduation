package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.ParamStringDao;
import by.mishota.graduation.dao.StudentDao;
import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Student;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class StudentDaoImpl implements StudentDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;
    public static final String USER_N_T_FOUND_FOR_STUDENT = "User isn't found for student";

    private static final String SELECT_ALL = "SELECT id,user_id, faculty_id, budget FROM students ";
    private static final String SELECT_ALL_MALE = "select students.id,user_id, faculty_id, budget from students join users u on students.user_id = u.id where gender='male'";
    private static final String SELECT_ALL_FEMALE = "select students.id,user_id, faculty_id, budget from students join users u on students.user_id = u.id where gender='female'";
    private static final String SELECT_ALL_PAYER = "select id,user_id, faculty_id, budget from students where budget=false";
    private static final String SELECT_ALL_FREER = "select id,user_id, faculty_id, budget from students where budget=true";
    private static final String SELECT_BY_ID = "SELECT id,user_id, faculty_id, budget FROM students where id=";
    private static final String SELECT_ALL_BY_FACULTY_ID = "select id,user_id, faculty_id, budget from students where faculty_id=";
    private static final String PARAM_BUDGET = "budget";


    @Override
    public List<Student> findAll() throws DaoException {
        return findStudents(SELECT_ALL);

    }

    @Override
    public List<Student> findAllByFacultyId(int facultyId) throws DaoException {
        return findStudents(SELECT_ALL_BY_FACULTY_ID + facultyId);

    }

    @Override
    public List<Student> findByFree() throws DaoException {
        return findStudents(SELECT_ALL_FREER);

    }

    @Override
    public List<Student> findAllPayer() throws DaoException {
        return findStudents(SELECT_ALL_PAYER);
    }

    @Override
    public List<Student> findAllFemale() throws DaoException {
        return findStudents(SELECT_ALL_FEMALE);
    }

    @Override
    public List<Student> findAllMale() throws DaoException {
        return findStudents(SELECT_ALL_MALE);
    }

    @Override
    public Optional<Student> findById(int id) throws DaoException {
        List<Student> students = findStudents(SELECT_BY_ID + id);

        if (students.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(students.get(0));
    }


    private List<Student> findStudents(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                Student student = parseStudent(resultSet);
                students.add(student);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return students;
    }

    private Student parseStudent(ResultSet resultSet) throws SQLException, DaoException {

        Student student = new Student();
        student.setId(resultSet.getInt(ParamStringDao.PARAM_STUDENT_ID));
        student.setBudget(resultSet.getBoolean(PARAM_BUDGET));
        student.setIdFaculty(resultSet.getInt(ParamStringDao.PARAM_STUDENT_FACULTY_ID));

        int userId = resultSet.getInt(ParamStringDao.PARAM_STUDENT_USER_ID);
        UserDao userDao = new UserDaoImpl();
        Optional<User> userOptional = userDao.findById(userId);

        if (userOptional.isPresent()) {
            student.setUser(userOptional.get());
        } else {
            throw new DaoException(USER_N_T_FOUND_FOR_STUDENT);
        }

        return student;

    }

}
