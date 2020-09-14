package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface EntrantDao extends Dao {

    int numberEntrants() throws DaoException;

    Optional<Entrant> findById(int id) throws DaoException;

    Optional<Entrant> findByUserId(int userId) throws DaoException;

    List<Entrant> findFewSkippingFew(int numberFind, int numberSkipping) throws DaoException;

    List<Entrant> findAll() throws DaoException;

    int update(Entrant entrant) throws DaoException, DuplicateException;

    int update(Entrant entrant, Connection connection) throws DaoException,DuplicateException;

    Optional<Entrant> add(Entrant entrant) throws DaoException,DuplicateException;

    Optional<Entrant> add(Entrant entrant, Connection connection) throws DaoException,DuplicateException;

    boolean delete(int id) throws DaoException;

    boolean delete(int id, Connection connection) throws DaoException;

    Optional<Entrant> addResult(Entrant entrant) throws DaoException,DuplicateException;

    Optional<Entrant> addResult(Entrant entrant, Connection connection) throws DaoException,DuplicateException;

    Optional<Entrant> addPriorities(Entrant entrant) throws DaoException,DuplicateException;

    Optional<Entrant> addPriorities(Entrant entrant, Connection connection) throws DaoException,DuplicateException;

    boolean deleteResult(Entrant entrant, SubjectResult subjectResult) throws DaoException, DuplicateException;

    boolean deleteResult(Entrant entrant, SubjectResult subjectResult, Connection connection) throws DaoException, DuplicateException;

    boolean deleteResults(Entrant entrant, List<SubjectResult> subjectResults) throws DaoException, DuplicateException;

    boolean deleteResults(Entrant entrant, List<SubjectResult> subjectResults, Connection connection) throws DaoException, DuplicateException;

    boolean deletePriority(Entrant entrant, FacultyPriority deletingFacultyPriority) throws DaoException, DuplicateException;

    boolean deletePriority(Entrant entrant, FacultyPriority deletingFacultyPriority, Connection connection) throws DaoException, DuplicateException;

    boolean deletePriorities(Entrant entrant, List<FacultyPriority> deletingFacultyPriorities) throws DaoException, DuplicateException;

    boolean deletePriorities(Entrant entrant, List<FacultyPriority> deletingFacultyPriorities, Connection connection) throws DaoException, DuplicateException;
}
