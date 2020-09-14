package by.mishota.graduation.dao;

import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface FacultyPriorityDao extends Dao {

    Optional<FacultyPriority> add(FacultyPriority facultyPriority, int entrantId) throws DaoException, DuplicateException;

    Optional<FacultyPriority> add(FacultyPriority facultyPriority, int entrantId, Connection connection) throws DaoException,DuplicateException;

    List<FacultyPriority> addAll(List<FacultyPriority> facultyPriorities, int entrantId) throws DaoException,DuplicateException;

    List<FacultyPriority> addAll(List<FacultyPriority> facultyPriorities, int entrantId, Connection connection) throws DaoException,DuplicateException;

    int update(FacultyPriority facultyPriority) throws DaoException,DuplicateException;

    int update(FacultyPriority facultyPriority, Connection connection) throws DaoException,DuplicateException;

    Optional<FacultyPriority> findById(int id) throws DaoException;

    List<FacultyPriority> findAll() throws DaoException;

    boolean delete(FacultyPriority facultyPriority) throws DaoException;

    boolean delete(FacultyPriority facultyPriority, Connection connection) throws DaoException;

    boolean deleteAllByEntrant(int entrantId) throws DaoException;

    boolean deleteAllByEntrant(int entrantId, Connection connection) throws DaoException;

    List<FacultyPriority> findAllByEntrantId(int entrantId) throws DaoException;

    List<FacultyPriority> findByFacultyId(int facultyId) throws DaoException;

}
