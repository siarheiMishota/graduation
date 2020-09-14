package by.mishota.graduation.dao;

import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface SubjectResultDao extends Dao {

    Optional<SubjectResult> findById(int id) throws DaoException;

    List<SubjectResult> findAllByEntrantId(int studentId) throws DaoException;

    Optional<SubjectResult> add(SubjectResult subjectResult, int entrantId) throws DaoException, DuplicateException;

    Optional<SubjectResult> add(SubjectResult subjectResult, int entrantId, Connection connection) throws DaoException, DuplicateException;

    int update(SubjectResult subjectResult, int entrantId) throws DaoException, DuplicateException;

    int update(SubjectResult subjectResult, int entrantId, Connection connection) throws DaoException, DuplicateException;

    boolean delete(SubjectResult subjectResult) throws DaoException, DuplicateException;

    boolean delete(SubjectResult subjectResult, Connection connection) throws DaoException, DuplicateException;
}
