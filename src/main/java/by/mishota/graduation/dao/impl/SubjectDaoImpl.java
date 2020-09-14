package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.VALUE_ATTRIBUTE_DUPLICATE;
import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQuerySubjectDao.*;

public class SubjectDaoImpl implements SubjectDao {

    @Override
    public Optional<Subject> add(Subject subject) throws DaoException, DuplicateException {
        if (subject==null){
            return Optional.empty();
        }
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return  add(subject, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Subject> add(Subject subject, Connection connection) throws DaoException, DuplicateException {
        if (subject==null){
            return Optional.empty();
        }
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
                    return Optional.of(subject);
                } else {
                    throw new DaoException("Creating subject failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error connecting to database", e);
        } finally {
            close(statement, generatedKeys);
        }
        return Optional.empty();
    }

    @Override
    public int update(Subject subject) throws DaoException, DuplicateException {
        if (subject==null){
            return -1;
        }
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
    public int update(Subject subject, Connection connection) throws DaoException, DuplicateException {
        if (subject==null){
            return -1;
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, subject.getName());
            preparedStatement.setInt(2, subject.getId());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting result", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public boolean delete(Subject subject) throws DaoException {
        if (subject==null) {
            return false;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return delete(subject, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Subject subject, Connection connection) throws DaoException {
        if (subject==null) {
            return false;
        }

        Statement statement = null;
        try {
            statement = connection.createStatement();
            int updatedRows = statement.executeUpdate(DELETE + subject.getId());
            return updatedRows != 0;

        } catch (SQLException e) {
            throw new DaoException("Error deletion subject by id", e);
        } finally {
            close(statement);
        }
    }

    @Override
    public Optional<Subject> findById(int id) throws DaoException {
        if (id<0){
            return Optional.empty();
        }
        List<Subject> subjects = findSubjects(SELECT_FIND_BY_ID + id);

        if (subjects.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(subjects.get(0));
    }

    @Override
    public List<Subject> findAllByFacultyId(int facultyId) throws DaoException {
        if (facultyId<0){
            return List.of();
        }
        return findSubjects(SELECT_FIND_ALL_BY_FACULTY + facultyId);
    }

    @Override
    public List<Subject> findAll() throws DaoException {
        return findSubjects(SELECT_ALL);
    }

    @Override
    public List<Integer> findAllIdByFacultyId(int facultyId) throws DaoException {
        if (facultyId<0){
            return List.of();
        }
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
}
