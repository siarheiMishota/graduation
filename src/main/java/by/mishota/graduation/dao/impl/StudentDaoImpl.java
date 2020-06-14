package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.StudentDao;
import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Student;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;
    private static Logger logger = LogManager.getLogger();

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public List<Student> findByFaculty() {
        return null;
    }

    @Override
    public List<Student> findByFree() {
        return null;
    }

    @Override
    public List<Student> findByPay() {
        return null;
    }

    @Override
    public List<Student> findAllFemale() {
        return null;
    }

    @Override
    public List<Student> findAllMale() {
        return null;
    }

    @Override
    public Optional<Student> findById() {
        return Optional.empty();
    }

    private List<Student> findStudents(String sqlRequest) throws DaoException {
        ConnectionPool pool;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Student> students = new ArrayList<>();
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
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
        student.setId(resultSet.getInt("id"));
        student.setBudget(resultSet.getBoolean("budget"));

        int userId = resultSet.getInt("user_id");
        UserDao userDao = new UserDaoImpl();
        Optional<User> userOptional = userDao.findById(userId);

        if (userOptional.isPresent()) {
            student.setUser(userOptional.get());
        } else {
            throw new DaoException("User isn't found for student");
        }

        return student;

    }

}
