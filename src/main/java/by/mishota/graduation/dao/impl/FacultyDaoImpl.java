package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.StudentDao;
import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

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
    public static final String SELECT_ALL = "select id, name, number_pay_places, number_free_places from faculties";
    public static final String SELECT_BY_ID = "select id, name, number_pay_places, number_free_places from faculties where id=";
    public static final String SELECT_WHERE_FREE_PLACES_MORE_SPECIFY = "select id, name, number_pay_places, number_free_places from faculties where number_free_places>";
    public static final String SELECT_WHERE_FREE_PLACES_LESS_SPECIFY = "select id, name, number_pay_places, number_free_places from faculties where number_free_places<";

    @Override
    public List<Faculty> findAll() throws DaoException {

        return findFaculties(SELECT_ALL);
    }

    @Override
    public Optional<Faculty> findById(int id) throws DaoException {

        List<Faculty> faculties = findFaculties(SELECT_BY_ID + id + ";");

        if (faculties.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(faculties.get(0));
    }

    @Override
    public List<Faculty> findWhereFreePlacesMoreSpecify(int specifyPlaces) throws DaoException {
        return findFaculties(SELECT_WHERE_FREE_PLACES_MORE_SPECIFY + specifyPlaces + ";");
    }

    @Override
    public List<Faculty> findWhereFreePlacesLessSpecify(int specifyPlaces) throws DaoException {
        return findFaculties(SELECT_WHERE_FREE_PLACES_LESS_SPECIFY + specifyPlaces + ";");
    }


    private List<Faculty> findFaculties(String sqlRequest) throws DaoException {
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

    private Faculty parseFaculty(ResultSet resultSet) throws SQLException, DaoException {

        Faculty faculty = new Faculty();
        int facultyId = resultSet.getInt(PARAM_SUBJECT_ID);

        faculty.setId(facultyId);
        faculty.setName(resultSet.getString(PARAM_FACULTY_NAME));
        faculty.setNumberFreePlaces(resultSet.getInt(PARAM_FACULTY_NUMBER_FREE_PLACES));
        faculty.setNumberPayPlaces(resultSet.getInt(PARAM_FACULTY_NUMBER_PAY_PLACES));

        SubjectDao subjectDao = new SubjectDaoImpl();
        List<Integer> needIdSubjectsForFaculty = subjectDao.findAllIdByFacultyId(facultyId);
        faculty.setIdNeedSubjects(needIdSubjectsForFaculty);

        StudentDao studentDao=new StudentDaoImpl();
        List<Integer> idStudents=studentDao.findAllIdByFacultyId(facultyId);
        faculty.setIdStudents(idStudents);
        return faculty;
    }


}
