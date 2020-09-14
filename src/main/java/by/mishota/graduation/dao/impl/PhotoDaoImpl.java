package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.EntityTransaction;
import by.mishota.graduation.dao.PhotoDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.VALUE_ATTRIBUTE_DUPLICATE;
import static by.mishota.graduation.dao.impl.SqlColumnName.USER_PHOTO_ID;
import static by.mishota.graduation.dao.impl.SqlColumnName.USER_PHOTO_NAME;
import static by.mishota.graduation.dao.sql_query.SqlQueryPhotosDao.*;

public class PhotoDaoImpl implements PhotoDao {

    @Override
    public boolean add(User user, String name) throws DaoException, DuplicateException {
        if (user == null || name == null) {
            return false;
        }
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return add(user, name, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean add(User user, String name, Connection connection) throws DaoException, DuplicateException {
        if (user == null || name == null) {
            return false;
        }
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, name);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting id of the photo", e);
        } finally {
            close(preparedStatement, generatedKeys);
        }

        return true;
    }

    @Override
    public boolean addAll(User user, List<String> names) throws DaoException, DuplicateException {
        if (user == null || names == null || names.isEmpty()) {
            return false;
        }

        EntityTransaction entityTransaction = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            if (!addAll(user, names, entityTransaction.getConnection())) {
                entityTransaction.rollback();
                return false;
            }
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

            throw new DaoException("Error setting transaction in photoDao", e);
        } finally {
            close(connection);
        }
        return true;
    }

    @Override
    public boolean addAll(User user, List<String> names, Connection connection) throws DaoException, DuplicateException {
        if (user == null || names == null || names.isEmpty()) {
            return false;
        }

        for (String name : names) {
            if (!add(user, name, connection)) {
                return false;
            }
        }
        return true;
    }

    @Override

    public int update(int id, String name) throws DaoException, DuplicateException {
        if (id < 0 || name == null) {
            return -1;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return update(id, name, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public int update(int id, String name, Connection connection) throws DaoException, DuplicateException {
        if (id < 0 || name == null) {
            return -1;
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

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
    public boolean delete(String name) throws DaoException {
        if (name == null) {
            return false;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return delete(name, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(String name, Connection connection) throws DaoException {
        if (name == null) {
            return false;
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setString(1, name);

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Error getting result when deleting photo", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public boolean deleteSome(List<String> names) throws DaoException {
        if (names == null || names.isEmpty()) {
            return false;
        }

        EntityTransaction entityTransaction = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            for (String name : names) {
                if (!delete(name, entityTransaction.getConnection())) {
                    entityTransaction.rollback();
                    return false;
                }
            }
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
            throw new DaoException("Error setting transaction in photoDao", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<String> findAll() throws DaoException {
        return findPhotos(SELECT_ALL);
    }

    @Override
    public List<String> findAllByUserId(int userId) throws DaoException {
        if (userId < 0) {
            return List.of();
        }

        return findPhotos(SELECT_ALL_BY_USER_ID + userId);
    }

    @Override
    public Optional<Integer> findIdByName(String name) throws DaoException {
        if (name == null) {
            return Optional.empty();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_NAME);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return Optional.of(resultSet.getInt(USER_PHOTO_ID));
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> findById(int id) throws DaoException {
        if (id < 0) {
            return Optional.empty();
        }

        List<String> photos = findPhotos(SELECT_BY_ID + id);
        if (photos.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(photos.get(0));
    }

    private List<String> findPhotos(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> photos = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                photos.add(resultSet.getString(USER_PHOTO_NAME));
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return photos;
    }


}
