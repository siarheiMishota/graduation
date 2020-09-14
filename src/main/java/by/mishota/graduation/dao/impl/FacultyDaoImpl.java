package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.StudentDao;
import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.VALUE_ATTRIBUTE_DUPLICATE;
import static by.mishota.graduation.dao.impl.SqlColumnName.*;
import static by.mishota.graduation.dao.sql_query.SqlQueryFacultyDao.*;

public class FacultyDaoImpl implements FacultyDao {


    @Override
    public int update(Faculty faculty) throws DaoException, DuplicateException {
        if (faculty == null) {
            return -1;
        }

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
    public int update(Faculty faculty, Connection connection) throws DaoException, DuplicateException {
        if (faculty == null) {
            return -1;
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, faculty.getName());
            preparedStatement.setInt(2, faculty.getNumberFreePlaces());
            preparedStatement.setInt(3, faculty.getNumberPayPlaces());
            preparedStatement.setInt(4, faculty.getId());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting result", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public Optional<Faculty> add(Faculty faculty) throws DaoException, DuplicateException {
        if (faculty == null) {
            return Optional.empty();
        }

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
    public Optional<Faculty> add(Faculty faculty, Connection connection) throws DaoException, DuplicateException {
        if (faculty == null) {
            return Optional.empty();
        }
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
                throw new DuplicateException(e);
            }
            throw new DaoException("Error getting id of the faculty", e);
        } finally {
            close(preparedStatement, generatedKeys);
        }

        return Optional.of(faculty);
    }

    @Override
    public boolean deleteById(int id) throws DaoException {
        if (id < 0) {
            return false;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return deleteById(id, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean deleteById(int id, Connection connection) throws DaoException {
        if (id < 0) {
            return false;
        }

        Statement statement = null;

        try {
            statement = connection.createStatement();
            int updatedRows = statement.executeUpdate(DELETE + id);
            return updatedRows != 0;

        } catch (SQLException e) {
            throw new DaoException("Error deletion faculty by id", e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean deleteByName(String name) throws DaoException {
        if (name == null) {
            return false;
        }
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            return deleteByName(name, connection);

        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection from connection pool", e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean deleteByName(String name, Connection connection) throws DaoException {
        if (name == null) {
            return false;
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_BY_NAME);
            preparedStatement.setString(1, name);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new DaoException("Error deletion user by name", e);
        } finally {
            close(preparedStatement);
        }
    }

    @Override
    public int numberFaculties() throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_NUMBER_ALL_FACULTIES);

            while (resultSet.next()) {
                return resultSet.getInt(1);
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
    public List<Faculty> findAll() throws DaoException {
        return findFaculties(SELECT_ALL);
    }

    @Override
    public List<Faculty> findAllByNeedSubjects(List<Integer> idNeedSubjects) throws DaoException {
        if (idNeedSubjects == null || idNeedSubjects.isEmpty()) {
            return List.of();
        }
        List<Faculty> allFaculties = findAll();
        List<Faculty> returnFaculty = new ArrayList<>();
        for (Faculty faculty : allFaculties) {
            boolean contains = faculty.getIdNeedSubjects().containsAll(idNeedSubjects);
            boolean contains1 = idNeedSubjects.containsAll(faculty.getIdNeedSubjects());
            if (contains && contains1) {
                returnFaculty.add(faculty);
            }
        }
        return returnFaculty;
    }

    @Override
    public List<Faculty> findFewSkippingFew(int numberFind, int numberSkipping) throws DaoException {
        if (numberFind < 0 || numberSkipping < 0) {
            return List.of();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Faculty> faculties = new ArrayList<>();
        int place = 1;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SELECT_FACULTIES_OFFSET_FEW_SKIPPING_FEW_);
            preparedStatement.setInt(place++, numberSkipping);
            preparedStatement.setInt(place++, numberFind);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Faculty faculty = parseFaculty(resultSet);
                faculties.add(faculty);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return faculties;
    }

    @Override
    public Optional<Faculty> findById(int id) throws DaoException {
        if (id < 0) {
            return Optional.empty();
        }

        List<Faculty> faculties = findFaculties(SELECT_BY_ID + id + ";");

        if (faculties.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(faculties.get(0));
    }

    @Override
    public Optional<Faculty> findByName(String name) throws DaoException {
        if (name == null || name.isEmpty()) {
            return Optional.empty();
        }

        List<Faculty> faculties = findFaculties(SELECT_BY_NAME + name + "';");

        if (faculties.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(faculties.get(0));
    }

    @Override
    public List<Faculty> findWhereFreePlacesMoreSpecify(int specifyPlaces) throws DaoException {
        if (specifyPlaces < 0) {
            return List.of();
        }
        return findFaculties(SELECT_WHERE_FREE_PLACES_MORE_SPECIFY + specifyPlaces + ";");
    }

    @Override
    public List<Faculty> findWhereFreePlacesLessSpecify(int specifyPlaces) throws DaoException {
        if (specifyPlaces < 0) {
            return List.of();
        }
        return findFaculties(SELECT_WHERE_FREE_PLACES_LESS_SPECIFY + specifyPlaces + ";");
    }

    @Override
    public boolean isExistId(int id) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_FACULTY_IS_EXIST + id);

            while (resultSet.next()) {
                return resultSet.getInt(1) == 1;
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return false;
    }


    private List<Faculty> findFaculties(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Faculty> faculties = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
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
}
