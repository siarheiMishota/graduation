package by.mishota.graduation.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface Dao {
    int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    Logger logger = LogManager.getLogger();

    default void close(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn("Result set isn't  closed", e);
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.warn("Statement isn't  closed", e);

            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.warn("Connection isn't closed", e);
            }
        }
    }

    default void close(Statement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.warn("Statement isn't  closed", e);

            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.warn("Result set isn't  closed", e);
            }
        }
    }

    default void close(Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.warn("Statement isn't  closed", e);

            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.warn("Connection isn't closed", e);
            }
        }
    }

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.warn("Statement isn't  closed", e);

            }
        }
    }

    default void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.warn("Connection isn't closed", e);
            }
        }
    }
}
