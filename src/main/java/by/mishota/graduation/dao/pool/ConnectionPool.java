package by.mishota.graduation.dao.pool;

import by.mishota.graduation.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final String PATH_DATABASE_PROPERTIES = "src/main/resources/database";
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
                String url = properties.getProperty("url");

                Connection connection = new ProxyConnection(DriverManager.getConnection(url, properties));
                freeConnections.put(new ProxyConnection(connection));

            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Connection pool isn't initialized", e);
            } catch (FileNotFoundException e) {
                throw new ConnectionPoolException("File database.properties for configuration isn't found", e);
            } catch (IOException e) {
                throw new ConnectionPoolException("Error reading configuration file", e);
            }
        }

        if (freeConnections.size()!= DEFAULT_POOL_SIZE){
            throw new ExceptionInInitializerError("Failed to initialize the pool connection where not all " +
                    "connection are created");
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

        for (int i = 0; i < freeConnections.size(); i++) {
            try {
                freeConnections.take();
            } catch (InterruptedException e) {
                logger.warn("Connection isn't closed");
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        Properties properties=new Properties();
        properties.load(new FileInputStream(PATH_DATABASE_PROPERTIES));
        String url = properties.getProperty("url");

        Connection connection = new ProxyConnection(DriverManager.getConnection(url, properties));
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users ");

        resultSet.next();
        String login = resultSet.getString("login");
        System.out.println(login);

        try {
            resultSet.previous();
            System.out.println(1);
            resultSet.previous();
            System.out.println(2);
            resultSet.previous();
            resultSet.previous();
            resultSet.previous();
            resultSet.previous();
            resultSet.previous();
            resultSet.previous();
            resultSet.previous();
            resultSet.previous();

        }catch (Exception e){
            System.out.println("УРААААААА");
        }

    }



}