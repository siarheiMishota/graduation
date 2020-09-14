package by.mishota.graduation.dao.pool;

import by.mishota.graduation.exception.ConnectionPoolException;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.testng.Assert.*;

public class ConnectionPoolTest {

    @Test(groups = "connectionPool")
    public void testGetInstance() throws ConnectionPoolException {
        ConnectionPool connectionPool1 = ConnectionPool.getInstance();
        ConnectionPool connectionPool2 = ConnectionPool.getInstance();

        assertTrue(connectionPool1 == connectionPool2);
    }

    @Test(timeOut = 2000, groups = "connectionPool")
    public void testTimeGettingInstance() throws ConnectionPoolException {
        ConnectionPool.getInstance();
    }

    @Test(groups = "connectionPool")
    public void testGetPoolSize() throws ConnectionPoolException {
        int defaultPoolSize = 14;
        assertEquals(defaultPoolSize, ConnectionPool.getInstance().getPoolSize());
    }

    @Test(groups = "connectionPool")
    public void testGetConnection() throws ConnectionPoolException, SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        assertTrue(connection instanceof ProxyConnection);
        connection.close();

    }

    @Test( groups = "connectionPool")
    public void testReleaseConnection() throws ConnectionPoolException, SQLException {
        ConnectionPool connectionPool=ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        int freeSizeAfterGetConnection = connectionPool.getPoolFreeSize();
        connection.close();

        assertEquals(freeSizeAfterGetConnection+1,connectionPool.getPoolSize());




    }

    @Test(dependsOnGroups = "connectionPool",expectedExceptions = ConnectionPoolException.class, expectedExceptionsMessageRegExp = "Pool was closed and can't get a connection")
    public void testClosedPool() throws ConnectionPoolException {
        ConnectionPool instance = ConnectionPool.getInstance();
        instance.closedPool();
        instance.getConnection();
    }
}