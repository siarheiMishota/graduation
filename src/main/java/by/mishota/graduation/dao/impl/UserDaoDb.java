package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.UserDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoDb implements UserDao {

    private static Logger logger = LogManager.getLogger();

    //language=MySQL
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SELECT_FIND_BY_ID = "SELECT * FROM users where id=";
    private static final String SELECT_ALL_MALE = "SELECT * FROM users where gender='male'";
    private static final String SELECT_ALL_FEMALES="SELECT * FROM users where gender='female'";
    private static final String SELECT_ALL_ADULT="SELECT * FROM users where date_birth  <DATE_ADD(CURDATE(), INTERVAL -18 YEAR )";


    private static final String SELECT_ALL_ADULTS="SELECT * FROM users where date_birth > NOW()";


    @Override
    public List<User> findAll() throws UserDaoException {
        return parseUsers(SELECT_ALL_USERS);
    }

    @Override
    public List<User> findAllMales() throws UserDaoException {
        return parseUsers(SELECT_ALL_MALE);
    }

    @Override
    public List<User> findAllFemales() throws UserDaoException {
        return parseUsers(SELECT_ALL_FEMALES);
    }

    @Override
    public List<User> findAllAdults() throws UserDaoException {
        return parseUsers(SELECT_ALL_ADULTS);
    }

    @Override
    public Optional<User> findById(int id) throws UserDaoException {
        List<User> users = parseUsers(SELECT_FIND_BY_ID);

        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }


    private List<User> parseUsers(String sqlRequest) throws UserDaoException {
        ConnectionPool pool = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sqlRequest);

            while (rs.next()) {
                User user = parseUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new UserDaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new UserDaoException("Error getting connection", e);
        } finally {
            close(pool, connection, statement, rs);
        }
        return users;
    }

    private User parseUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int passportId = rs.getInt("passport_id");
        LocalDate date_birth = rs.getDate("date_birth").toLocalDate();
        String login = rs.getString("login");
        String password = rs.getString("password");
        String email = rs.getString("email");
        String firstName = rs.getString("first_name");
        String surname = rs.getString("surname");
        String fatherName = rs.getString("father_name");
        Gender gender = Gender.valueOfIgnoreCase(rs.getString("gender"));
        boolean confirmed = rs.getBoolean("confirmed");
        Path pathToFile = Path.of(rs.getString("photo"));

        return User.create(id, passportId, date_birth, login, password, email, firstName, surname, fatherName, gender, confirmed, pathToFile);
    }

    private void close(ConnectionPool pool, Connection connection, Statement statement, ResultSet rs) throws UserDaoException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new UserDaoException("Error closing result set");
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new UserDaoException("Error closing statement");
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new UserDaoException("Error closing connection");
            }
        }
    }


}