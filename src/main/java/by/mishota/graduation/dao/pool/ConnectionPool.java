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
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String PATH_DATABASE_PROPERTIES = "src/main/resources/database";
    public static final String CAN_T_RETURN_THE_CONNECTION_TO_THE_POOL = "Can't return the connection to the pool that is not from the pool";
    public static final String CLOSING_CONNECTION_WAS_INTERRUPTED = "Closing connection was interrupted";
    public static final String FAILED_TO_INITIALIZE = "Failed to initialize the pool connection where not all connection are created";
    public static final String ERROR_READING_CONFIGURATION = "Error reading configuration file";
    public static final String FILE_FOR_CONFIGURATION_ISN_T_FOUND = "File database.properties for configuration isn't found";
    public static final String CONNECTION_POOL_ISN_T_INITIALIZED = "Connection pool isn't initialized";
    public static final String PARAM_URL = "url";
    public static final String CONNECTION_ISN_T_CLOSED = "Connection isn't closed";
    public static final String PARAM_LOGIN = "login";

    private static final int DEFAULT_POOL_SIZE = 12;
    private static ConnectionPool instance;
    private static Logger logger = LogManager.getLogger();
    private static boolean poolClosed = false;
    private static Lock lock = new ReentrantLock();


    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> busyConnections;

    public static ConnectionPool getInstance() throws ConnectionPoolException {

        if (instance == null) {
            try {
                lock.lock();

                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }

        if (poolClosed) {
            throw new ConnectionPoolException("Pool was closed and can't get the instance");
        }
        return instance;
    }


    private ConnectionPool() throws ConnectionPoolException {
        freeConnections = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE, true);
        busyConnections = new ArrayDeque<>();

        Properties properties = new Properties();

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                properties.load(new FileInputStream(PATH_DATABASE_PROPERTIES));
                String url = properties.getProperty(PARAM_URL);

                Connection connection = new ProxyConnection(DriverManager.getConnection(url, properties));
                freeConnections.put(new ProxyConnection(connection));

            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException(CONNECTION_POOL_ISN_T_INITIALIZED, e);
            } catch (FileNotFoundException e) {
                throw new ConnectionPoolException(FILE_FOR_CONFIGURATION_ISN_T_FOUND, e);
            } catch (IOException e) {
                throw new ConnectionPoolException(ERROR_READING_CONFIGURATION, e);
            }
        }

        if (freeConnections.size() != DEFAULT_POOL_SIZE) {
            throw new ExceptionInInitializerError(FAILED_TO_INITIALIZE);
        }
    }

    public int getPoolSize() {
        return DEFAULT_POOL_SIZE;
    }

    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;

        if (poolClosed) {
            throw new ConnectionPoolException("Pool was closed and can't get a connection");
        }
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
            if (connection instanceof by.mishota.graduation.dao.pool.ProxyConnection && busyConnections.contains(connection)) {
                freeConnections.put((by.mishota.graduation.dao.pool.ProxyConnection) connection);
                busyConnections.poll();
            } else {
                throw new ConnectionPoolException(CAN_T_RETURN_THE_CONNECTION_TO_THE_POOL);
            }
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(CLOSING_CONNECTION_WAS_INTERRUPTED, e);
        }

        if (poolClosed) {
            closedPool();
        }
    }

    public void closedPool() {

        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                freeConnections.take();
            } catch (InterruptedException e) {
                logger.warn(CONNECTION_ISN_T_CLOSED);
            }
        }
    }
}