package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.EntrantDao;
import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.impl.SqlColumnName.*;

public class EntrantDaoImpl implements EntrantDao {
    @Override
    public Optional<Entrant> findById(int id) throws DaoException {//todo
        return Optional.empty();

    }

    @Override
    public List<Entrant> findAllByFaculty(int idFaculty) throws DaoException {//todo
        return null;
    }

    @Override
    public int numberEntrantMoreSpecify(int numberSpecify) {//todo
        return 0;
    }

    @Override
    public int numberEntrantEqualsSpecify(int numberSpecify) {//todo
        return 0;
    }

    private List<Entrant> findEntrants(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Entrant> entrants = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                Entrant entrant = parseEntrant(resultSet);
                entrants.add(entrant);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return entrants;
    }

    private Entrant parseEntrant(ResultSet resultSet) throws SQLException, DaoException {

        Entrant entrant = new Entrant();

        int entrantId = resultSet.getInt(ENTRANT_ID);

        entrant.setId(entrantId);
        entrant.setCertificate(resultSet.getInt(ENTRANT_CERTIFICATE));

        int userId = resultSet.getInt(ENTRANT_USER_ID);
        UserDao dao = DaoFactory.getInstance().getUserDao();
        Optional<User> userOptional = dao.findById(entrantId);

        if (userOptional.isEmpty()) {
            throw new DaoException(new StringBuilder().append("user wasn't found for entrant, where entrant_id= ")
                    .append(entrantId)
                    .append(", and user_id= ")
                    .append(userId)
                    .toString());
        }

        entrant.setUser(userOptional.get());
        return entrant;
    }
}
