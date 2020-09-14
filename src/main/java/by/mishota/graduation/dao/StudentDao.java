package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Student;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface StudentDao extends Dao {

    List<Student> findAll() throws DaoException;

    List<Student> findAllByFacultyId(int facultyId) throws DaoException;

    List<Integer> findAllIdByFacultyId(int facultyId) throws DaoException;

    List<Student> findAllFree() throws DaoException;

    List<Student> findAllPayer() throws DaoException;

    List<Student> findAllFemale() throws DaoException;

    List<Student> findAllMale() throws DaoException;

    Optional<Student> findById(int id) throws DaoException;

    int update(Student student) throws DaoException;

    int update(Student student, Connection connection) throws DaoException;

    Optional<Student> add(Student student) throws DaoException, DuplicateException;

    Optional<Student> add(Student student, Connection connection) throws DaoException, DuplicateException;

    boolean delete(Student student) throws DaoException;

    boolean delete(Student student, Connection connection) throws DaoException;

}
