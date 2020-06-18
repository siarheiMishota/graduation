package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.ParamStringDao;
import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

import static by.mishota.graduation.dao.ParamStringDao.*;

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

                String name = resultSet.getString(ParamStringDao.PARAM_SUBJECT_NAME);
                Subject subject = new Subject();
                subject.setId(id);
                subject.setName(name);


                return Optional.of(subject);
            }
        } catch (SQLException e) {
            throw new DaoException(ERROR_GETTING_RESULT, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(ERROR_GETTING_CONNECTION, e);
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
                        throw new DaoException(CREATING_SUBJECT_FAILED_NO_ID_OBTAINED);
                    }
                } catch (Exception e) {

                }
            }


        } catch (SQLException e) {

            if (e.getErrorCode() ==DUPLICATE_ENTRY_ERROR_CODE){
                throw new DaoException(CANNOT_INSERT_A_DUPLICATE_SUBJECT_,e);
            }
                System.out.println(e.getErrorCode());
            throw new DaoException(ERROR_CONNECTING_TO_DATABASE, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(ERROR_GETTING_CONNECTION, e);
        } finally {
            close(connection, statement, generatedKeys);
        }
    }

}
