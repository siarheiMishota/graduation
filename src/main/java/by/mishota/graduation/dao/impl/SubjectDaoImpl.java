package by.mishota.graduation.dao.impl;

import by.mishota.graduation.dao.ParamStringDao;
import by.mishota.graduation.dao.SubjectDao;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.ConnectionPoolException;
import by.mishota.graduation.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.dao.ParamStringDao.*;

public class SubjectDaoImpl implements SubjectDao {

    private static final int DUPLICATE_ENTRY_ERROR_CODE = 1062;

    private static final String SELECT_FIND_BY_ID = "SELECT id, name FROM subjects where id=";
    private static final String SELECT_FIND_ALL_BY_FACULTY = "select subjects.id, name, faculties_id, subject_id from subjects join subjects_to_faculties stf on subjects.id = stf.subject_id where faculties_id=";
    private static final String ADD = "INSERT INTO subjects(name) value (?);";
    public static final String PARAM_NAME = "name";

    @Override
    public void add(Subject subject) throws DaoException {
        ConnectionPool pool;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet generatedKeys = null;
        try {
            pool = ConnectionPool.getInstance();
            connection = pool.getConnection();
            statement = connection.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, subject.getName());

            int numberUpdatedLines = statement.executeUpdate();

            if (numberUpdatedLines == 1) {
                try {
                    generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        subject.setId(generatedKeys.getInt(1));
                    } else {
                        throw new DaoException("Creating subject failed, no ID obtained.");
                    }
                } catch (Exception e) {

                }
            }


        } catch (SQLException e) {

            if (e.getErrorCode() == DUPLICATE_ENTRY_ERROR_CODE) {
                throw new DaoException("Cannot insert a duplicate subject ", e);
            }
            System.out.println(e.getErrorCode());
            throw new DaoException("Error connecting to database", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, generatedKeys);
        }
    }

    @Override
    public Optional<Subject> findById(int id) throws DaoException {
        List<Subject> subjects = findSubjects(SELECT_FIND_BY_ID + id);

        if (subjects.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(subjects.get(0));
    }

    @Override
    public List<Subject> findAllByFacultyId(int facultyId) throws DaoException {
        return findSubjects(SELECT_FIND_ALL_BY_FACULTY + facultyId);

    }


    private List<Subject> findSubjects(String sqlRequest) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Subject> subjects = new ArrayList<>();
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlRequest);

            while (resultSet.next()) {
                Subject subject = parseSubject(resultSet);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            throw new DaoException("Error getting result", e);
        } catch (ConnectionPoolException e) {
            throw new DaoException("Error getting connection", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return subjects;
    }

    private Subject parseSubject(ResultSet resultSet) throws SQLException {

        Subject subject = new Subject();
        subject.setId(resultSet.getInt(ParamStringDao.PARAM_STUDENT_ID));
        subject.setName(resultSet.getString(PARAM_NAME));

        return subject;

    }


}
