package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class SubjectDaoImpl implements SubjectDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    private static final String FIND_BY_ID = "SELECT id, name FROM subjects where id=(?);";
    private static final String ADD = "INSERT INTO subjects(name) value (?);";

    private static Logger logger = LogManager.getLogger();

    @Override
    public Optional<Subject> findById(int id) throws DaoException {
        ConnectionPool pool;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String name = resultSet.getString("name");
                Subject subject = new Subject();
                subject.setId(id);
                subject.setName(name);


                return Optional.of(subject);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }

        return Optional.empty();
    }

    @Override
    public void add(Subject subject) throws DaoException {
        ConnectionPool pool;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, subject.getName());

            int numberUpdatedLines = statement.executeUpdate();

            if (numberUpdatedLines == 1) {
                try {
                    generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        subject.setId(generatedKeys.getInt(1));
                    } else {
                        throw new DaoException("Creating subject failed, no ID obtained.");
                    }
                } catch (Exception e) {

                }
            }


        } catch (SQLException e) {

            if (e.getErrorCode() ==DUPLICATE_ENTRY_ERROR_CODE){
                throw new DaoException("cannot insert a duplicate subject ",e);
            }
                System.out.println(e.getErrorCode());
            throw new DaoException("Error connecting to database", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, generatedKeys);
        }
    }

}
