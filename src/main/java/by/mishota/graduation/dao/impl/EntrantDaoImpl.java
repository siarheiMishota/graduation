package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.*;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQueryEntrantDao.*;

public class EntrantDaoImpl implements EntrantDao {

    @Override
    public Optional<Entrant> add(Entrant entrant) throws DaoException, DuplicateException {
        if (entrant == null) {
            return Optional.empty();
        }

        Connection connection = null;
        EntityTransaction entityTransaction = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            add(entrant, entityTransaction.getConnection());
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
            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            close(connection);
        }

        return Optional.of(entrant);

    }

    @Override
    public Optional<Entrant> add(Entrant entrant, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null) {
            return Optional.empty();
        }

        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        int place = 1;

        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(place++, entrant.getUser().getId());
            preparedStatement.setInt(place++, entrant.getCertificate());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entrant.setId(generatedKeys.getInt(1));
            }

            SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();
            FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();

            if (entrant.getResults() != null) {
                for (SubjectResult result : entrant.getResults()) {
                    subjectResultDao.add(result, entrant.getId(), connection);
                }
            }

            if (entrant.getPriorities() != null) {
                facultyPriorityDao.addAll(entrant.getPriorities(), entrant.getId(), connection);

            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting id of the entrant", e);
        } finally {
            close(preparedStatement, generatedKeys);
        }

        return Optional.of(entrant);
    }

    @Override
    public int update(Entrant entrant) throws DaoException, DuplicateException {
        if (entrant == null) {
            return -1;
        }

        Connection connection = null;
        EntityTransaction entityTransaction = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            int numberUpdated = update(entrant, entityTransaction.getConnection());
            entityTransaction.finish();
            return numberUpdated;

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }

            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            close(connection);
        }

    }

    @Override
    public int update(Entrant entrant, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null) {
            return -1;
        }

        PreparedStatement preparedStatement = null;
        int place = 1;
        int numberUpdated;

        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setInt(place++, entrant.getUser().getId());
            preparedStatement.setInt(place++, entrant.getCertificate());
            preparedStatement.setTimestamp(place++, Timestamp.valueOf(entrant.getDate()));
            preparedStatement.setInt(place++, entrant.getId());

            numberUpdated = preparedStatement.executeUpdate();

            FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
            SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();

            for (FacultyPriority facultyPriority : entrant.getPriorities()) {
                facultyPriorityDao.update(facultyPriority, connection);
            }

            for (SubjectResult subjectResult : entrant.getResults()) {
                subjectResultDao.update(subjectResult, entrant.getId(), connection);
            }

            return numberUpdated;
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
    public boolean delete(int id) throws DaoException {
        if (id < 0) {
            return false;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return delete(id, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(int id, Connection connection) throws DaoException {
        if (id < 0) {
            return false;
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            int updatedRows = statement.executeUpdate(DELETE + id);
            return updatedRows != 0;

        } catch (SQLException e) {
            throw new DaoException("Error deletion entrant by id", e);
        } finally {
            close(statement);
        }
    }

    @Override
    public Optional<Entrant> addResult(Entrant entrant) throws DaoException, DuplicateException {
        if (entrant == null) {
            return Optional.empty();
        }
        Connection connection = null;
        EntityTransaction entityTransaction = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);

            entityTransaction.start();
            addResult(entrant, connection);
            entityTransaction.finish();

            return Optional.of(entrant);

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }

            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }

            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Entrant> addResult(Entrant entrant, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null) {
            return Optional.empty();
        }
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();

        if (entrant.getResults() == null) {
            entrant.setPriorities(new ArrayList<>());
        }
        for (SubjectResult result : entrant.getResults()) {
            if (subjectResultDao.findById(result.getId()).isEmpty()) {
                subjectResultDao.add(result, entrant.getId(), connection);
            }
        }
        if (!entrant.getPriorities().isEmpty()) {
            boolean deletePriorities = DaoFactory.getInstance().getFacultyPriorityDao().deleteAllByEntrant(entrant.getId(), connection);
            if (deletePriorities) {
                entrant.getPriorities().clear();
            }
        }
        return Optional.of(entrant);
    }

    @Override
    public Optional<Entrant> addPriorities(Entrant entrant) throws DaoException, DuplicateException {
        if (entrant == null) {
            return Optional.empty();
        }

        EntityTransaction entityTransaction = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();
            addPriorities(entrant, entityTransaction.getConnection());
            entityTransaction.finish();
            return Optional.of(entrant);

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }

            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Entrant> addPriorities(Entrant entrant, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null) {
            return Optional.empty();
        }

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();

        if (entrant.getPriorities() == null) {
            entrant.setPriorities(new ArrayList<>());
        }
        List<FacultyPriority> entrantFacultyPriorities = facultyPriorityDao.findAllByEntrantId(entrant.getId());

        for (FacultyPriority facultyPriority : entrant.getPriorities()) {
            if (!entrantFacultyPriorities.contains(facultyPriority)) {
                facultyPriorityDao.add(facultyPriority, entrant.getId(), connection);
            }
        }

        return Optional.of(entrant);
    }

    @Override
    public boolean deleteResult(Entrant entrant, SubjectResult subjectResult) throws DaoException, DuplicateException {
        if (entrant == null || subjectResult == null) {
            return false;
        }
        Connection connection = null;
        EntityTransaction entityTransaction = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            return deleteResult(entrant, subjectResult, entityTransaction.getConnection());

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            try {
                entityTransaction.finish();
            } catch (SQLException e) {
                throw new DaoException("Error closing transaction manager", e);
            }
            close(connection);
        }
    }

    @Override
    public boolean deleteResult(Entrant entrant, SubjectResult subjectResult, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null || subjectResult == null) {
            return false;
        }
        boolean deletedResult = DaoFactory.getInstance().getSubjectResultDao().delete(subjectResult, connection);

        if (deletedResult) {
            entrant.getResults().remove(subjectResult);
            if (!entrant.getPriorities().isEmpty()) {
                if (DaoFactory.getInstance().getFacultyPriorityDao().deleteAllByEntrant(entrant.getId(), connection)) {
                    entrant.setPriorities(new ArrayList<>());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteResults(Entrant entrant, List<SubjectResult> subjectResults, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null || subjectResults == null) {
            return false;
        }

        for (SubjectResult subjectResult : subjectResults) {
            if (!deleteResult(entrant, subjectResult, connection)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean deleteResults(Entrant entrant, List<SubjectResult> subjectResults) throws DaoException, DuplicateException {
        if (entrant == null || subjectResults == null) {
            return false;
        }

        Connection connection = null;
        EntityTransaction entityTransaction = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            deleteResults(entrant, subjectResults, entityTransaction.getConnection());
            entityTransaction.finish();
            return true;

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean deletePriority(Entrant entrant, FacultyPriority facultyPriority) throws DaoException, DuplicateException {
        if (entrant == null || facultyPriority == null) {
            return false;
        }

        Connection connection = null;
        EntityTransaction entityTransaction = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            return deletePriority(entrant, facultyPriority, entityTransaction.getConnection());

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            try {
                entityTransaction.finish();
            } catch (SQLException e) {
                throw new DaoException("Error closing transaction manager", e);
            }
            close(connection);
        }
    }

    @Override
    public boolean deletePriority(Entrant entrant, FacultyPriority deletingFacultyPriority, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null || deletingFacultyPriority == null) {
            return false;
        }

        boolean deleted = DaoFactory.getInstance().getFacultyPriorityDao().delete(deletingFacultyPriority, connection);

        if (deleted) {

            entrant.getPriorities().remove(deletingFacultyPriority);

            int currentPriority = 1;
            for (FacultyPriority facultyPriority : entrant.getPriorities()) {
                if (facultyPriority.getPriority() != currentPriority) {
                    facultyPriority.setPriority(currentPriority);
                }
                currentPriority++;
            }

            return update(entrant, connection) != 0;
        }
        return false;
    }

    @Override
    public boolean deletePriorities(Entrant entrant, List<FacultyPriority> deletingFacultyPriorities) throws DaoException, DuplicateException {
        if (entrant == null || deletingFacultyPriorities == null) {
            return false;
        }

        Connection connection = null;
        EntityTransaction entityTransaction = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            deletePriorities(entrant, deletingFacultyPriorities, entityTransaction.getConnection());
            entityTransaction.finish();
            return true;

        } catch (ConnectionPoolException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error getting connection from connection pool", e);
        } catch (SQLException e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DaoException("Error setting transaction in entrantDao", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean deletePriorities(Entrant entrant, List<FacultyPriority> deletingFacultyPriorities, Connection connection) throws DaoException, DuplicateException {
        if (entrant == null || deletingFacultyPriorities == null) {
            return false;
        }

        for (FacultyPriority facultyPriority : deletingFacultyPriorities) {
            if (!deletePriority(entrant, facultyPriority, connection)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<Entrant> findById(int id) throws DaoException {
        if (id < 0) {
            return Optional.empty();
        }
        List<Entrant> entrants = findEntrants(SELECT_FIND_BY_ID + id + ";");

        if (entrants.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(entrants.get(0));
    }

    @Override
    public Optional<Entrant> findByUserId(int userId) throws DaoException {
        if (userId < 0) {
            return Optional.empty();
        }

        List<Entrant> entrants = findEntrants(SELECT_FIND_BY_USER_ID + userId + ";");

        if (entrants.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(entrants.get(0));
    }

    @Override
    public List<Entrant> findFewSkippingFew(int numberFind, int numberSkipping) throws DaoException {
        if (numberFind < 0 || numberSkipping < 0) {
            return List.of();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Entrant> entrants = new ArrayList<>();
        int place = 1;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_OFFSET_FEW_SKIPPING_FEW);
            preparedStatement.setInt(place++, numberSkipping);
            preparedStatement.setInt(place++, numberFind);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Entrant entrant = parseEntrant(resultSet);
                entrants.add(entrant);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return entrants;
    }

    @Override
    public List<Entrant> findAll() throws DaoException {
        return findEntrants(SELECT_FIND_ALL);
    }

    @Override
    public int numberEntrants() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_NUMBER_ALL_ENTRANTS);

            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return 0;
    }

    private List<Entrant> findEntrants(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Entrant> entrants = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                Entrant entrant = parseEntrant(resultSet);
                entrants.add(entrant);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return entrants;
    }

    private Entrant parseEntrant(ResultSet resultSet) throws SQLException, DaoException {

        Entrant entrant = new Entrant();

        int entrantId = resultSet.getInt(ENTRANT_ID);

        entrant.setId(entrantId);
        entrant.setCertificate(resultSet.getInt(ENTRANT_CERTIFICATE));
        entrant.setDate(resultSet.getTimestamp(ENTRANT_DATE).toLocalDateTime());

        int userId = resultSet.getInt(ENTRANT_USER_ID);
        UserDao dao = DaoFactory.getInstance().getUserDao();
        Optional<User> userOptional = dao.findById(userId);

        if (userOptional.isEmpty()) {
            throw new DaoException(new StringBuilder().append("user wasn't found for entrant, where entrant_id= ")
                    .append(entrantId)
                    .append(", and user_id= ")
                    .append(userId)
                    .toString());
        }

        entrant.setUser(userOptional.get());

        FacultyPriorityDao facultyPriorityDao = DaoFactory.getInstance().getFacultyPriorityDao();
        SubjectResultDao subjectResultDao = DaoFactory.getInstance().getSubjectResultDao();

        List<FacultyPriority> facultyPriorities = facultyPriorityDao.findAllByEntrantId(entrantId);
        List<SubjectResult> subjectResults = subjectResultDao.findAllByEntrantId(entrantId);
        entrant.setPriorities(facultyPriorities);
        entrant.setResults(subjectResults);
        return entrant;
    }
}
