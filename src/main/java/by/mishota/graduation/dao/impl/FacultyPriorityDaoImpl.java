package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.EntityTransaction;
import by.mishota.graduation.dao.FacultyPriorityDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQueryFacultyPriorityDao.*;

public class FacultyPriorityDaoImpl implements FacultyPriorityDao {

    @Override
    public boolean delete(FacultyPriority facultyPriority, Connection connection) throws DaoException {

        if (facultyPriority == null) {
            return false;
        }
        Statement statement = null;
        ResultSet generatedKeys = null;
        try {
            statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(DELETE + facultyPriority.getId());

            return affectedRows != 0;

        } catch (SQLException e) {
            throw new DaoException("Error deleting id of the faculty priority", e);
        } finally {
            close(statement, generatedKeys);
        }
    }

    @Override
    public boolean delete(FacultyPriority facultyPriority) throws DaoException {
        if (facultyPriority == null) {
            return false;
        }
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return delete(facultyPriority, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error deleting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean deleteAllByEntrant(int entrantId) throws DaoException {
        if (entrantId < 0) {
            return false;
        }
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return deleteAllByEntrant(entrantId, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error deleting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean deleteAllByEntrant(int entrantId, Connection connection) throws DaoException {
        if (entrantId < 0) {
            return false;
        }
        Statement statement = null;
        ResultSet generatedKeys = null;
        try {
            statement = connection.createStatement();

            int affectedRows = statement.executeUpdate(DELETE_ALL_BY_ENTRANT + entrantId);

            return affectedRows != 0;

        } catch (SQLException e) {
            throw new DaoException("Error deleting all by entrant of the faculty priority", e);
        } finally {
            close(statement, generatedKeys);
        }
    }

    @Override
    public Optional<FacultyPriority> add(FacultyPriority facultyPriority, int entrantId) throws DaoException, DuplicateException {
        if (facultyPriority == null) {
            return Optional.empty();
        }
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return add(facultyPriority, entrantId, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<FacultyPriority> add(FacultyPriority facultyPriority, int entrantId, Connection connection) throws DaoException, DuplicateException {

        if (facultyPriority == null) {
            return Optional.empty();
        }

        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        int place = 1;

        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(place++, entrantId);
            preparedStatement.setInt(place++, facultyPriority.getFacultyId());
            preparedStatement.setInt(place++, facultyPriority.getPriority());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                facultyPriority.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting id of the faculty priority", e);
        } finally {
            close(preparedStatement, generatedKeys);
        }

        return Optional.of(facultyPriority);
    }


    @Override
    public List<FacultyPriority> addAll(List<FacultyPriority> facultyPriorities, int entrantId) throws DaoException, DuplicateException {
        if (facultyPriorities == null || facultyPriorities.isEmpty()) {
            return List.of();
        }
        EntityTransaction entityTransaction = null;
        Connection connection = null;
        List<FacultyPriority> resultFacultyPriorities = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            resultFacultyPriorities.addAll(addAll(facultyPriorities, entrantId, entityTransaction.getConnection()));
            entityTransaction.finish();

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error adding new priorities in facultyPriorityDao", e);
        } finally {
            close(connection);
        }
        return resultFacultyPriorities;
    }

    @Override
    public List<FacultyPriority> addAll(List<FacultyPriority> facultyPriorities, int entrantId, Connection connection) throws DaoException, DuplicateException {
        if (facultyPriorities == null || facultyPriorities.isEmpty()) {
            return List.of();
        }
        List<FacultyPriority> resultFacultyPriority = new ArrayList<>();
        for (FacultyPriority facultyPriority : facultyPriorities) {
            Optional<FacultyPriority> added = add(facultyPriority, entrantId, connection);
            added.ifPresent(resultFacultyPriority::add);
        }
        return resultFacultyPriority;
    }

    @Override
    public int update(FacultyPriority facultyPriority, Connection connection) throws DaoException, DuplicateException {
        if (facultyPriority == null) {
            return -1;
        }
        PreparedStatement preparedStatement = null;
        int place = 1;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(place++, facultyPriority.getFacultyId());
            preparedStatement.setInt(place++, facultyPriority.getPriority());
            preparedStatement.setInt(place++, facultyPriority.getId());

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
    public int update(FacultyPriority facultyPriority) throws DaoException, DuplicateException {
        if (facultyPriority == null) {
            return -1;
        }
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return update(facultyPriority, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<FacultyPriority> findAll() throws DaoException {
        return findFacultyPriorities(SELECT_FIND_ALL);
    }

    @Override
    public List<FacultyPriority> findAllByEntrantId(int entrantId) throws DaoException {
        if (entrantId < 0) {
            return List.of();
        }
        return findFacultyPriorities(SELECT_FIND_ALL_BY_ENTRANT_ID + entrantId);
    }

    @Override
    public Optional<FacultyPriority> findById(int id) throws DaoException {
        if (id < 0) {
            return Optional.empty();
        }
        List<FacultyPriority> facultyPriorities = findFacultyPriorities(SELECT_FIND_BY_ID + id);

        if (facultyPriorities.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(facultyPriorities.get(0));
    }

    @Override
    public List<FacultyPriority> findByFacultyId(int facultyId) throws DaoException {
        if (facultyId < 0) {
            return List.of();
        }
        return findFacultyPriorities(SELECT_FIND_ALL_BY_FACULTY_ID + facultyId);
    }

    private List<FacultyPriority> findFacultyPriorities(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<FacultyPriority> facultyPriorities = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                FacultyPriority facultyPriority = parseFacultyPriority(resultSet);
                facultyPriorities.add(facultyPriority);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return facultyPriorities;
    }

    private FacultyPriority parseFacultyPriority(ResultSet resultSet) throws SQLException, DaoException {

        FacultyPriority facultyPriority = new FacultyPriority();
        facultyPriority.setId(resultSet.getInt(FACULTY_PRIORITY_ID));
        facultyPriority.setFacultyId(resultSet.getInt(FACULTY_PRIORITY_FACULTY_ID));
        facultyPriority.setPriority(resultSet.getInt(FACULTY_PRIORITY_PRIORITY));

        return facultyPriority;
    }
}
