package by.mishota.graduation.dao.pool;

import by.mishota.graduation.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String PARAM_URL = "url";
    private static final String PARAM_DRIVER = "driver";
    private static final int DEFAULT_POOL_SIZE = 12;

    private static String PATH_DATABASE_PROPERTIES = "prop/database.properties";
    private static ConnectionPool instance;
    private static Logger logger = LogManager.getLogger();
    private static boolean poolClosed = false;
    private static Lock lock = new ReentrantLock();
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    private BlockingQueue<ProxyConnection> freeConnections;
    private Set<ProxyConnection> busyConnections;
    private int poolSize;

    public static ConnectionPool getInstance() throws ConnectionPoolException {

        if (!atomicBoolean.get()) {
            try {
                lock.lock();

                if (instance == null) {
                    instance = new ConnectionPool();
                    atomicBoolean.set(true);
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

    public static void setPathDatabaseProperties(String path) {
        PATH_DATABASE_PROPERTIES = path;
    }

    private ConnectionPool() throws ConnectionPoolException {
        busyConnections = new HashSet<>();
        Properties properties = new Properties();

        try (InputStream is = getClass().getClassLoader()
                .getResourceAsStream(PATH_DATABASE_PROPERTIES)) {

            properties.load(is);
            String url = properties.getProperty(PARAM_URL);
            String stringPoolSize = properties.getProperty("pool.size");

            if (stringPoolSize == null) {
                poolSize = DEFAULT_POOL_SIZE;
            } else {
                try {
                    poolSize = Integer.parseInt(stringPoolSize);
                } catch (NumberFormatException e) {
                    poolSize = DEFAULT_POOL_SIZE;
                    logger.warn("Pool size in property field is incorrect, check your property field", e);
                }
            }

            freeConnections = new ArrayBlockingQueue<>(poolSize, true);
            Class.forName(properties.getProperty(PARAM_DRIVER));

            for (int i = 0; i < poolSize; i++) {
                freeConnections.put(new ProxyConnection(DriverManager.getConnection(url, properties)));
            }

        } catch (SQLException | InterruptedException | IOException | ClassNotFoundException e) {
            throw new ConnectionPoolException("Connection pool isn't initialized", e);
        }

        if (freeConnections.size() != poolSize) {
            throw new ExceptionInInitializerError("Failed to initialize the pool connection where not all connection are created");
        }

    }


    public int getPoolSize() {
        return poolSize;
    }

    public int getPoolFreeSize() {
        return freeConnections.size();
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
            if (connection instanceof ProxyConnection) {
                if (busyConnections.contains(connection)) {
                    freeConnections.put((ProxyConnection) connection);
                    busyConnections.remove(connection);
                }
            } else {
                throw new ConnectionPoolException("Can't return the connection to the pool that is not from the pool");
            }
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Closing connection was interrupted", e);
        }

        if (poolClosed) {
            closedPool();
        }
    }

    public void closedPool() {
        poolClosed = true;
        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                freeConnections.take();
            } catch (InterruptedException e) {
                logger.warn("Connection isn't closed");
            }
        }

        if (freeConnections.isEmpty() && busyConnections.isEmpty()) {
            DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
                try {
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    logger.warn("Driver doesn't deregister", e);
                }
            });
        }
    }
}