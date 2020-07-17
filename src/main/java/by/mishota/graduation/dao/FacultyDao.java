package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface FacultyDao extends Dao {

    List<Faculty> findAll() throws DaoException;

    Optional<Faculty> findById(int id) throws DaoException;

    List<Faculty> findWhereFreePlacesMoreSpecify(int specifyPlaces) throws DaoException;

    List<Faculty> findWhereFreePlacesLessSpecify(int specifyPlaces) throws DaoException;

    Optional<Faculty> add(Faculty faculty) throws DaoException;

    Optional<Faculty> add(Faculty faculty, Connection connection) throws DaoException;

    int update(Faculty faculty) throws DaoException;

    int update(Faculty faculty, Connection connection) throws DaoException;

}
