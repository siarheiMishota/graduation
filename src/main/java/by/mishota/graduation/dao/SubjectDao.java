package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface SubjectDao extends Dao {

    Optional<Subject> add(Subject subject) throws DaoException, DuplicateException;

    Optional<Subject> add(Subject subject, Connection connection) throws DaoException, DuplicateException;

    int update(Subject subject) throws DaoException, DuplicateException;

    int update(Subject subject, Connection connection) throws DaoException, DuplicateException;

    boolean delete(Subject subject) throws DaoException;

    boolean delete(Subject subject, Connection connection) throws DaoException;


    Optional<Subject> findById(int id) throws DaoException;

    List<Subject> findAll() throws DaoException;

    List<Subject> findAllByFacultyId(int facultyId) throws DaoException;

    List<Integer> findAllIdByFacultyId(int facultyId) throws DaoException;


}
