package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface SubjectDao extends Dao {

    Optional<Subject> findById(int id) throws DaoException;

    void add(Subject subject) throws DaoException;

    void add(Subject subject, Connection connection) throws DaoException;

    int update(Subject subject) throws DaoException;

    int update(Subject subject, Connection connection) throws DaoException;

    List<Subject> findAllByFacultyId(int facultyId) throws DaoException;

    List<Integer> findAllIdByFacultyId(int facultyId) throws DaoException;
}
