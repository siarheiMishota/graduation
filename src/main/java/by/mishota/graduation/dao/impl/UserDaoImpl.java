package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    private static Logger logger = LogManager.getLogger();

    /*language=MySQL*/
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SELECT_FIND_BY_ID = "SELECT * FROM users where id=";
    private static final String SELECT_FIND_BY_LOGIN = "SELECT * FROM users where login=";
    private static final String SELECT_ALL_MALE = "SELECT * FROM users where gender='male'";
    private static final String SELECT_ALL_FEMALES = "SELECT * FROM users where gender='female'";
    private static final String SELECT_ALL_ADULT = "SELECT * FROM users where date_birth  <DATE_ADD(CURDATE(), INTERVAL -18 YEAR )";
    private static final String SELECT_ALL_ADULTS = "SELECT * FROM users where date_birth > NOW()";
    private static final String INSERT_NEW_USER = "INSERT INTO users( passport_id, date_birth, login, password, email," +
            " first_name, surname, father_name, gender, confirmed) value(?,?,?,?,?,?,?,?,?,?)";


    @Override
    public List<User> findAll() throws DaoException {
        return findUsers(SELECT_ALL_USERS);
    }

    @Override
    public List<User> findAllMales() throws DaoException {
        return findUsers(SELECT_ALL_MALE);
    }

    @Override
    public List<User> findAllFemales() throws DaoException {
        return findUsers(SELECT_ALL_FEMALES);
    }

    @Override
    public List<User> findAllAdults() throws DaoException {
        return findUsers(SELECT_ALL_ADULTS);
    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        List<User> users = findUsers(SELECT_FIND_BY_ID);

        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        List<User> users = findUsers(SELECT_FIND_BY_LOGIN);

        if (users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> add(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, user.getPassportId());
            preparedStatement.setDate(2, Date.valueOf(user.getBirth()));
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getFirstName());
            preparedStatement.setString(7, user.getSurname());
            preparedStatement.setString(8, user.getFatherName());
            preparedStatement.setString(9, user.getGender().toString());
            preparedStatement.setBoolean(10, user.isConfirmed());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DaoException("cannot insert a duplicate user ", e);
            }
            throw new DaoException("Error getting id of the user", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, preparedStatement, generatedKeys);
        }

        return Optional.of(user);

    }

    private List<User> findUsers(String sqlRequest) throws DaoException {
        ConnectionPool pool;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<>();
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                User user = parseUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return users;
    }


    private User parseUser(ResultSet resultSet) throws SQLException {
        User.Builder builder = new User.Builder();

        builder.setId(resultSet.getInt("id"));
        builder.setPassportId(resultSet.getInt("passport_id"));
        builder.setBirth(resultSet.getDate("date_birth").toLocalDate());
        builder.setLogin(resultSet.getString("login"));
        builder.setPassword(resultSet.getString("password"));
        builder.setEmail(resultSet.getString("email"));
        builder.setFirstName(resultSet.getString("first_name"));
        builder.setSurname(resultSet.getString("surname"));
        builder.setFatherName(resultSet.getString("father_name"));
        builder.setGender(Gender.valueOfIgnoreCase(resultSet.getString("gender")));
        builder.setConfirmed(resultSet.getBoolean("confirmed"));
        builder.setPathToPhoto(Path.of(resultSet.getString("photo")));

        return builder.build();
    }


}