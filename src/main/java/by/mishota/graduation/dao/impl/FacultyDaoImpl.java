package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacultyDaoImpl implements FacultyDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    private static Logger logger = LogManager.getLogger();

    @Override
    public List<Faculty> findAll() {
        return null;
    }

    @Override
    public Optional<Faculty> findById() {
        return Optional.empty();
    }

    @Override
    public List<Faculty> findWhereFreeMoreSpecify(int specifyPlaces) {
        return null;
    }

    private List<Faculty> findFaculty(String sqlRequest) throws DaoException {
        ConnectionPool pool;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Faculty> faculties = new ArrayList<>();
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                Faculty faculty = parseFaculty(resultSet);
                faculties.add(faculty);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return faculties;
    }


    private Faculty parseFaculty(ResultSet resultSet) throws SQLException {

        Faculty faculty = new Faculty();

        faculty.setId(resultSet.getInt("id"));
        faculty.setName(resultSet.getString("name"));
        faculty.setNumberFreePlaces(resultSet.getInt("number_free_places"));
        faculty.setNumberPayPlaces(resultSet.getInt("number_pay_places"));
        faculty.setNeedSubjects(resultSet.getInt("need_subjects"));
    }



}
