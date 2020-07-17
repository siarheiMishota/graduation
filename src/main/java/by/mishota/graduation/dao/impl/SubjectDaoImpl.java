package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQuerySubjectDao.*;

public class SubjectDaoImpl implements SubjectDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;


    public static final String PARAM_NAME = "name";

    @Override
    public void add(Subject subject) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            add(subject, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public void add(Subject subject, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, subject.getName());

            int numberUpdatedLines = statement.executeUpdate();

            if (numberUpdatedLines == 1) {
                generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    subject.setId(generatedKeys.getInt(1));
                } else {
                    throw new DaoException("Creating subject failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {

            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DaoException("Cannot insert a duplicate subject ", e);
            }
            throw new DaoException("Error connecting to database", e);
        } finally {
            close(statement, generatedKeys);
        }
    }



    @Override
    public int update(Subject subject, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, subject.getName());
            preparedStatement.setInt(2, subject.getId());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public int update(Subject subject) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return update(subject, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Subject> findById(int id) throws DaoException {
        List<Subject> subjects = findSubjects(SELECT_FIND_BY_ID + id);

        if (subjects.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(subjects.get(0));
    }

    @Override
    public List<Subject> findAllByFacultyId(int facultyId) throws DaoException {
        return findSubjects(SELECT_FIND_ALL_BY_FACULTY + facultyId);

    }

    @Override
    public List<Integer> findAllIdByFacultyId(int facultyId) throws DaoException {
        return findIdSubjects(SELECT_FIND_ALL_BY_FACULTY + facultyId);

    }

    private List<Integer> findIdSubjects(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Integer> subjects = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                subjects.add(resultSet.getInt(STUDENT_ID));
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return subjects;
    }

    private List<Subject> findSubjects(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Subject> subjects = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                Subject subject = parseSubject(resultSet);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return subjects;
    }

    private Subject parseSubject(ResultSet resultSet) throws SQLException {

        Subject subject = new Subject();
        subject.setId(resultSet.getInt(SUBJECT_ID));
        subject.setName(resultSet.getString(SUBJECT_NAME));

        return subject;

    }

    public static void main(String[] args) throws DaoException {
        SubjectDao subjectDao=new SubjectDaoImpl();
        Optional<Subject> byId = subjectDao.findById(17);
        byId.get().setName("Хихимия");
        subjectDao.update(byId.get());
    }


}
