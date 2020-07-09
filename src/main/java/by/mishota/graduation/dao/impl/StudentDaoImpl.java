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

import static by.mishota.graduation.dao.SqlColumnName.*;
import static by.mishota.graduation.dao.SqlQueryStudentDao.*;


public class StudentDaoImpl implements StudentDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;




    @Override
    public List<Student> findAll() throws DaoException {
        return findStudents(SELECT_ALL);

    }

    @Override
    public List<Student> findAllByFacultyId(int facultyId) throws DaoException {
        return findStudents(SELECT_ALL_BY_FACULTY_ID + facultyId);

    }

    @Override
    public List<Integer> findAllIdByFacultyId(int facultyId) throws DaoException {
        return findIdStudents(SELECT_ALL_BY_FACULTY_ID + facultyId);
    }

    private List<Integer> findIdStudents(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Integer> idStudents = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                idStudents.add(resultSet.getInt(STUDENT_ID_COLUMN_NAME));
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return idStudents;
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
        student.setId(resultSet.getInt(STUDENT_ID_COLUMN_NAME));
        student.setBudget(resultSet.getBoolean(STUDENT_BUDGET_COLUMN_NAME));
        student.setIdFaculty(resultSet.getInt(STUDENT_FACULTY_ID_COLUMN_NAME));

        int userId = resultSet.getInt(STUDENT_USER_ID_COLUMN_NAME);
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
