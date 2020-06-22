package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface FacultyDao extends Dao {

    List<Faculty> findAll() throws DaoException;

    Optional<Faculty> findById(int id) throws DaoException;

    List<Faculty> findWhereFreePlacesMoreSpecify(int specifyPlaces) throws DaoException;

    List<Faculty> findWhereFreePlacesLessSpecify(int specifyPlaces) throws DaoException;

}
