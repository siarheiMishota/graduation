package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.SubjectResultDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQuerySubjectResultDao.*;

public class SubjectResultDaoImpl implements SubjectResultDao {//todo

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    @Override
    public Optional<SubjectResult> findById(int id) throws DaoException {
        List<SubjectResult> found = findSubjectResults(SELECT_BY_ID + id);
        if (found.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(found.get(0));
    }

    @Override
    public List<SubjectResult> findAllByEntrantId(int entrantId) throws DaoException {
        return findSubjectResults(SELECT_ALL_BY_ENTRANT_ID + entrantId);
    }


    @Override
    public Optional<SubjectResult> add(SubjectResult subjectResult, int entrantId, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, entrantId);
            preparedStatement.setInt(2, subjectResult.getSubjectId());
            preparedStatement.setInt(3, subjectResult.getPoints());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                subjectResult.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DaoException("Cannot insert a duplicate subject result ", e);
            }
            throw new DaoException("Error getting id of the subject result", e);
        } finally {
            close(preparedStatement, generatedKeys);
        }

        return Optional.of(subjectResult);
    }

    @Override
    public Optional<SubjectResult> add(SubjectResult subjectResult, int entrantId) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return add(subjectResult, entrantId, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }


    @Override
    public int update(SubjectResult subjectResult, int entrantId, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_SUBJECT_RESULT_BY_ID);
            preparedStatement.setInt(1, entrantId);
            preparedStatement.setInt(2, subjectResult.getSubjectId());
            preparedStatement.setInt(3, subjectResult.getPoints());
            preparedStatement.setInt(4, subjectResult.getId());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public int update(SubjectResult subjectResult, int entrantId) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return update(subjectResult, entrantId, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    private List<SubjectResult> findSubjectResults(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<SubjectResult> subjectResults = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                SubjectResult subjectResult = parseSubjectResult(resultSet);
                subjectResults.add(subjectResult);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return subjectResults;
    }

    private SubjectResult parseSubjectResult(ResultSet resultSet) throws SQLException, DaoException {

        SubjectResult subjectResult = new SubjectResult();

        subjectResult.setId(resultSet.getInt(SUBJECT_RESULT_ID));
        subjectResult.setPoints(resultSet.getInt(SUBJECT_RESULT_POINTS));
        subjectResult.setSubjectId(resultSet.getInt(SUBJECT_RESULT_SUBJECT_ID));

        return subjectResult;
    }

    public static void main(String[] args) throws DaoException {//todo
        SubjectResultDao subjectResultDao = new SubjectResultDaoImpl();
        Optional<SubjectResult> byId = subjectResultDao.findById(24);
        if (byId.isPresent()) {
            byId.get().setPoints(5);
            System.out.println(subjectResultDao.update(byId.get(), 4));
        }
    }
}