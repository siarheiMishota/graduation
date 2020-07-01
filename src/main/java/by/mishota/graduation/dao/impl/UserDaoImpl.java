package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.ParamStringDao.*;

public class UserDaoImpl implements UserDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SELECT_FIND_BY_ID = "SELECT * FROM users where id=";
    private static final String SELECT_FIND_BY_LOGIN = "SELECT * FROM users where login=";
    private static final String SELECT_FIND_BY_EMAIL = "SELECT * FROM users where email=";
    private static final String SELECT_ALL_MALE = "SELECT * FROM users where gender='male'";
    private static final String SELECT_ALL_FEMALES = "SELECT * FROM users where gender='female'";
    private static final String SELECT_ALL_ADULT = "SELECT * FROM users where date_birth  <DATE_ADD(CURDATE(), INTERVAL -18 YEAR )"; //TODO
    private static final String SELECT_ALL_ADULTS = "SELECT * FROM users where date_birth > NOW()";

    private static final String SELECT_FIND_COUNT_BY_PASSPORT_ID = "select count(*) as count from users where passport_id='";
    private static final String SELECT_FIND_COUNT_BY_LOGIN = "select count(*) as count from users where login='";
    private static final String SELECT_FIND_COUNT_BY_EMAIL = "select count(*) as count from users where email='";

    private static final String INSERT_NEW_USER = "INSERT INTO users( passport_id, date_birth, login, password, email," +
            " first_name, surname, father_name, gender, confirmed) value(?,?,?,?,?,?,?,?,?,?)";
    public static final String PARAM_COUNT = "count";


    @Override
    public int findCountByEmail(String email) throws DaoException {
        return countRows(SELECT_FIND_COUNT_BY_EMAIL + email + "'");
    }

    @Override
    public int findCountByLogin(String login) throws DaoException {
        return countRows(SELECT_FIND_COUNT_BY_LOGIN + login + "'");

    }

    @Override
    public int findCountByPassportId(String passportId) throws DaoException {
        return countRows(SELECT_FIND_COUNT_BY_PASSPORT_ID + passportId + "'");
    }

    private int countRows(String query) throws DaoException {
        ConnectionPool pool;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                return resultSet.getInt(PARAM_COUNT);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return 0;
    }


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
        List<User> users = findUsers(SELECT_FIND_BY_ID + id);

        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        List<User> users = findUsers(SELECT_FIND_BY_LOGIN + "'" + login + "'");

        if (users.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        List<User> users = findUsers(SELECT_FIND_BY_LOGIN + "'" + email + "'");

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

            preparedStatement.setString(1, user.getPassportId());
            preparedStatement.setDate(2, Date.valueOf(user.getBirth()));
            preparedStatement.setString(3, user.getLogin().toLowerCase());
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
                throw new DaoException(CANNOT_INSERT_DUPLICATE_USER, e);
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

        builder.setId(resultSet.getInt(PARAM_USER_ID));
        builder.setPassportId(resultSet.getString(PARAM_USER_PASSPORT_ID));
        builder.setBirth(resultSet.getDate(PARAM_USER_DATE_BIRTH).toLocalDate());
        builder.setLogin(resultSet.getString(PARAM_USER_LOGIN));
        builder.setPassword(resultSet.getString(PARAM_USER_PASSWORD));
        builder.setEmail(resultSet.getString(PARAM_USER_EMAIL));
        builder.setFirstName(resultSet.getString(PARAM_USER_FIRST_NAME));
        builder.setSurname(resultSet.getString(PARAM_USER_SURNAME));
        builder.setFatherName(resultSet.getString(PARAM_USER_FATHER_NAME));
        builder.setGender(Gender.valueOfIgnoreCase(resultSet.getString(PARAM_USER_GENDER)));
        builder.setConfirmed(resultSet.getBoolean(PARAM_USER_CONFIRMED));
        builder.setRole(Role.valueOfIgnoreCase(resultSet.getString(PARAM_USER_ROLE)));
//        builder.setPathToPhoto(Path.of(resultSet.getString(PARAM_USER_PHOTO)));  //todo

        return builder.build();

    }
}