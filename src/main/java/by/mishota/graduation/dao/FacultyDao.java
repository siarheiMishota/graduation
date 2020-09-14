package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface FacultyDao extends Dao {

    int numberFaculties() throws DaoException;

    Optional<Faculty> add(Faculty faculty) throws DaoException,DuplicateException;

    Optional<Faculty> add(Faculty faculty, Connection connection) throws DaoException,DuplicateException;

    int update(Faculty faculty) throws DaoException, DuplicateException;

    int update(Faculty faculty, Connection connection) throws DaoException,DuplicateException;

    boolean deleteById(int id) throws DaoException;

    boolean deleteById(int id, Connection connection) throws DaoException;

    boolean deleteByName(String name) throws DaoException;

    boolean deleteByName(String name, Connection connection) throws DaoException;

    boolean isExistId(int id) throws DaoException;

    List<Faculty> findAll() throws DaoException;

    List<Faculty> findAllByNeedSubjects(List<Integer> idNeedSubjects) throws DaoException;

    List<Faculty> findFewSkippingFew(int numberFind, int numberSkipping) throws DaoException;

    Optional<Faculty> findById(int id) throws DaoException;
    Optional<Faculty> findByName(String name) throws DaoException;

    List<Faculty> findWhereFreePlacesMoreSpecify(int specifyPlaces) throws DaoException;

    List<Faculty> findWhereFreePlacesLessSpecify(int specifyPlaces) throws DaoException;


}
