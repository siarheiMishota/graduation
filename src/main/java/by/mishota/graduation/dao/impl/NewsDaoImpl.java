package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.EntityTransaction;
import by.mishota.graduation.dao.NewsDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.dao.sql_query.SqlQueryNewsDao;
import by.mishota.graduation.entity.News;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.VALUE_ATTRIBUTE_DUPLICATE;
import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQueryNewsDao.*;

public class NewsDaoImpl implements NewsDao {

    @Override
    public boolean addAll(List<News> news) throws DaoException, DuplicateException {
        if (news == null || news.isEmpty()) {
            return false;
        }

        EntityTransaction entityTransaction = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            if (!addAll(news, entityTransaction.getConnection())) {
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

            throw new DaoException("Error setting transaction in newsDao", e);
        } finally {
            close(connection);
        }
        return true;
    }

    @Override
    public boolean addAll(List<News> news, Connection connection) throws DaoException, DuplicateException {
        if (news == null || news.isEmpty()) {
            return false;
        }

        for (News newsForeach : news) {
            if (!add(newsForeach, connection)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean add(News news) throws DaoException, DuplicateException {
        if (news == null) {
            return false;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return add(news, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean add(News news, Connection connection) throws DaoException, DuplicateException {
        if (news == null) {
            return false;
        }
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, news.getNameRu());
            preparedStatement.setString(2, news.getNameEn());
            preparedStatement.setInt(3, news.getUserCreatorId());
            preparedStatement.setString(4, news.getBriefDescriptionRu());
            preparedStatement.setString(5, news.getBriefDescriptionEn());
            preparedStatement.setString(6, news.getEnglishVariable());
            preparedStatement.setString(7, news.getRussianVariable());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(news.getCreationDate()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return false;
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                news.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting id of the news", e);
        } finally {
            close(preparedStatement, generatedKeys);
        }

        return true;
    }

    @Override
    public int update(News news) throws DaoException, DuplicateException {
        if (news == null) {
            return -1;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return update(news, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public int update(News news, Connection connection) throws DaoException, DuplicateException {
        if (news == null) {
            return -1;
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, news.getNameRu());
            preparedStatement.setString(2, news.getNameEn());
            preparedStatement.setInt(3, news.getUserCreatorId());
            preparedStatement.setString(4, news.getBriefDescriptionRu());
            preparedStatement.setString(5, news.getBriefDescriptionEn());
            preparedStatement.setString(6, news.getEnglishVariable());
            preparedStatement.setString(7, news.getRussianVariable());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(news.getCreationDate()));
            preparedStatement.setInt(9, news.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting result for updating in newsDao", e);
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

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(SqlQueryNewsDao.DELETE);
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException("Error getting result when deleting news", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public boolean deleteSome(List<Integer> listId) throws DaoException {
        if (listId== null || listId.isEmpty()) {
            return false;
        }

        EntityTransaction entityTransaction = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            entityTransaction = new EntityTransaction(connection);
            entityTransaction.start();

            for (int id: listId) {
                if (!delete(id, entityTransaction.getConnection())) {
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
            throw new DaoException("Error setting transaction in newsDao", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public List<News> findAll() throws DaoException {
        return findAllNews(SELECT_FIND_ALL);
    }

    @Override
    public Optional<News> findById(int id) throws DaoException {
        if (id < 0) {
            return Optional.empty();
        }

        List<News> allNews = findAllNews(SELECT_FIND_BY_ID + id);
        if (allNews.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(allNews.get(0));
    }

    @Override
    public List<News> findFewSkippingFew(int numberFind, int numberSkipping) throws DaoException {
        if (numberFind < 0 || numberSkipping < 0) {
            return List.of();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<News> foundNews = new ArrayList<>();
        int place = 1;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_OFFSET_FES_SKIPPING_FEW);
            preparedStatement.setInt(place++, numberSkipping);
            preparedStatement.setInt(place++, numberFind);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                News news = parseNews(resultSet);
                foundNews.add(news);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return foundNews;
    }

    @Override
    public int numberNews() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_NUMBER_ALL_NEWS);

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

    private List<News> findAllNews(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<News> foundNews = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                News news = parseNews(resultSet);
                foundNews.add(news);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return foundNews;
    }

    private News parseNews(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setId(resultSet.getInt(NEWS_ID));
        news.setNameRu(resultSet.getString(NEWS_NAME_RU));
        news.setNameEn(resultSet.getString(NEWS_NAME_EN));
        news.setUserCreatorId(resultSet.getInt(NEWS_USER_CREATOR_ID));
        news.setBriefDescriptionRu(resultSet.getString(NEWS_BRIEF_DESCRIPTION_RU));
        news.setBriefDescriptionEn(resultSet.getString(NEWS_BRIEF_DESCRIPTION_EN));
        news.setEnglishVariable(resultSet.getString(NEWS_ENGLISH_VARIABLE));
        news.setRussianVariable(resultSet.getString(NEWS_RUSSIAN_VARIABLE));
        news.setCreationDate(resultSet.getTimestamp(NEWS_CREATION_DATE).toLocalDateTime());

        return news;
    }
}
