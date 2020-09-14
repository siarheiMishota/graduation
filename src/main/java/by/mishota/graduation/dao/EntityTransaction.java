package by.mishota.graduation.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private Logger logger = LogManager.getLogger();

    private Connection connection;

    public EntityTransaction(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection(){
        return  connection;
    }

    public void start() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void rollback(){
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.info("Rollback is error", e);

        }
    }

    public void finish() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }
}
