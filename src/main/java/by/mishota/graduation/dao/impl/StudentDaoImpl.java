package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.StudentDao;
import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Student;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQueryStudentDao.*;


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
                idStudents.add(resultSet.getInt(STUDENT_ID));
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


    @Override
    public int update(Student student, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(1, student.getUser().getId());
            preparedStatement.setInt(2, student.getIdFaculty());
            preparedStatement.setBoolean(3, student.isBudget());
            preparedStatement.setInt(4, student.getId());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public Optional<Student> add(Student student) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return add(student, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Student> add(Student student, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;

        try {
            statement = connection.prepareStatement(INSERT,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, student.getUser().getId());
            statement.setInt(2, student.getIdFaculty());
            statement.setBoolean(3, student.isBudget());

            int numberUpdatedLines = statement.executeUpdate();

            if (numberUpdatedLines == 0) {
                return Optional.empty();
            }

            if (numberUpdatedLines == 1) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    student.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Creating student failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DaoException("Cannot insert a duplicate student ", e);
            }
            throw new DaoException("Error connecting to database", e);
        } finally {
            close(statement, generatedKeys);
        }

        return Optional.of(student);
    }

    @Override
    public int update(Student student) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return update(student, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection);
        }
    }

    private Student parseStudent(ResultSet resultSet) throws SQLException, DaoException {

        Student student = new Student();
        student.setId(resultSet.getInt(STUDENT_ID));
        student.setBudget(resultSet.getBoolean(STUDENT_BUDGET));
        student.setIdFaculty(resultSet.getInt(STUDENT_FACULTY_ID));

        int userId = resultSet.getInt(STUDENT_USER_ID);
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Optional<User> userOptional = userDao.findById(userId);

        if (userOptional.isPresent()) {
            student.setUser(userOptional.get());
        } else {
            throw new DaoException("User isn't found for student");
        }

        return student;
    }

    public static void main(String[] args) throws DaoException, ConnectionPoolException {//todo
        StudentDao studentDao = new StudentDaoImpl();
        Student student = new Student();
        student.setId(41);
        student.setBudget(false);
        student.setIdFaculty(46);
        User user = new User.Builder().setId(19).build();
        student.setUser(user);
        System.out.println(studentDao.update(student));
    }

}
