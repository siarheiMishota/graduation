package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.StudentDao;
import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQueryFacultyDao.*;

public class FacultyDaoImpl implements FacultyDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    @Override
    public int update(Faculty faculty, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setInt(2, faculty.getNumberFreePlaces());
            preparedStatement.setInt(3, faculty.getNumberPayPlaces());
            preparedStatement.setInt(4, faculty.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public int update(Faculty faculty) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return update(faculty, connection);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Faculty> add(Faculty faculty) throws DaoException {
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return add(faculty, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public Optional<Faculty> add(Faculty faculty, Connection connection) throws DaoException {

        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setInt(2, faculty.getNumberFreePlaces());
            preparedStatement.setInt(3, faculty.getNumberPayPlaces());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                faculty.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DaoException("Cannot insert a duplicate faculty ", e);
            }
            throw new DaoException("Error getting id of the faculty", e);
        } finally {
            close(preparedStatement, generatedKeys);
        }

        return Optional.of(faculty);
    }

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
        int facultyId = resultSet.getInt(FACULTY_ID);

        faculty.setId(facultyId);
        faculty.setName(resultSet.getString(FACULTY_NAME));
        faculty.setNumberFreePlaces(resultSet.getInt(FACULTY_NUMBER_FREE_PLACES));
        faculty.setNumberPayPlaces(resultSet.getInt(FACULTY_NUMBER_PAY_PLACES));

        SubjectDao subjectDao = new SubjectDaoImpl();
        List<Integer> needIdSubjectsForFaculty = subjectDao.findAllIdByFacultyId(facultyId);
        faculty.setIdNeedSubjects(needIdSubjectsForFaculty);

        StudentDao studentDao = new StudentDaoImpl();
        List<Integer> idStudents = studentDao.findAllIdByFacultyId(facultyId);
        faculty.setIdStudents(idStudents);
        return faculty;
    }

    public static void main(String[] args) throws DaoException, ConnectionPoolException {
        FacultyDao facultyDao=new FacultyDaoImpl();
        Faculty faculty=new Faculty();
        faculty.setId(60);
        faculty.setName("fffffffffffffffffff");
        faculty.setNumberFreePlaces(5);
        faculty.setNumberPayPlaces(1);
        int add = facultyDao.update(faculty, ConnectionPool.getInstance().getConnection());
        System.out.println(add);
    }


}
