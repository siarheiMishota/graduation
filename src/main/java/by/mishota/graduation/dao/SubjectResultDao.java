package by.mishota.graduation.dao;

import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface SubjectResultDao extends Dao {

    Optional<SubjectResult> findById(int id) throws DaoException;

    List<SubjectResult> findAllByEntrantId(int studentId) throws DaoException;

    Optional<SubjectResult> add(SubjectResult subjectResult, int entrantId) throws DaoException;

    Optional<SubjectResult> add(SubjectResult subjectResult, int entrantId, Connection connection) throws DaoException;

    int update(SubjectResult subjectResult,int entrantId) throws DaoException;

    int update(SubjectResult subjectResult,int entrantId, Connection connection) throws DaoException;
}
