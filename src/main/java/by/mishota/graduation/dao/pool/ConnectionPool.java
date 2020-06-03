package by.mishota.graduation.dao.pool;

import by.mishota.graduation.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String PATH_DATABASE_PROPERTIES = "src/main/resources/properties/database.properties";
    private static ConnectionPool instance;
    private static Logger logger = LogManager.getLogger();

    private BlockingQueue<Connection> freeConnections;
    private Queue<Connection> busyConnections;
    private List<Connection> allConnections;
    private int poolSize = 12;
    private boolean fair = true;

    public static ConnectionPool getInstance() throws ConnectionPoolException {

        if (instance == null) {
            Lock instanceLock = new ReentrantLock();
            try {
                instanceLock.lock();

                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }


    private ConnectionPool() throws ConnectionPoolException {
        freeConnections = new ArrayBlockingQueue<>(poolSize, fair);
        busyConnections = new ArrayDeque<>();
        allConnections = new ArrayList<>();

        Properties properties = new Properties();

        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                properties.load(new FileInputStream(PATH_DATABASE_PROPERTIES));
                String url = properties.getProperty("url");

                Connection connection = new ProxyConnection(DriverManager.getConnection(url, properties));
                freeConnections.put(connection);
                allConnections.add(connection);

            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Connection pool isn't initialized", e);
            } catch (FileNotFoundException e) {
                throw new ConnectionPoolException("File database.properties for configuration isn't found", e);
            } catch (IOException e) {
                throw new ConnectionPoolException("Error reading configuration file", e);
            }
        }
    }


    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public Connection getConnection() throws ConnectionPoolException {

        Connection connection;
        try {
            connection = freeConnections.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Waiting for connection was interrupted");
        }
        busyConnections.add(connection);
        return connection;
    }


    public void releaseConnection(Connection connection) throws ConnectionPoolException {

        try {
            if (connection instanceof ProxyConnection) {
                freeConnections.put(connection);
                busyConnections.poll();
            } else {
                throw new ConnectionPoolException("Can't return the connection to the pool that is not from the pool");
            }
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Closing connection was interrupted", e);
        }
    }


}