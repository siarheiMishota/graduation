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

import static by.mishota.graduation.dao.ParamStringDao.*;

public class FacultyDaoImpl implements FacultyDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;


    private static Logger logger = LogManager.getLogger();

    @Override
    public List<Faculty> findAll() {
        return null;
    }//TODO

    @Override
    public Optional<Faculty> findById() {
        return Optional.empty();
    }//TODO

    @Override
    public List<Faculty> findWhereFreeMoreSpecify(int specifyPlaces) {
        return null;
    }//TODO

    private List<Faculty> findFaculty(String sqlRequest) throws DaoException {//TODO
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
            throw new DaoException(ERROR_GETTING_RESULT, e);
        } catch (ConnectionPoolException e) {
            throw new DaoException(ERROR_GETTING_CONNECTION, e);
        } finally {
            close(connection, statement, resultSet);
        }
        return faculties;
    }


    private Faculty parseFaculty(ResultSet resultSet) throws SQLException {//TODO

        Faculty faculty = new Faculty();

        faculty.setId(resultSet.getInt(PARAM_SUBJECT_ID));
        faculty.setName(resultSet.getString(PARAM_FACULTY_NAME));
        faculty.setNumberFreePlaces(resultSet.getInt(PARAM_FACULTY_NUMBER_FREE_PLACES));
        faculty.setNumberPayPlaces(resultSet.getInt(PARAM_FACULTY_NUMBER_PAY_PLACES));
//        faculty.setNeedSubjects(resultSet.getInt("need_subjects"));
        return faculty;
    }


}
