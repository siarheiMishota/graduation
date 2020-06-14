package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.DaoException;

import java.util.Optional;

public interface SubjectDao extends Dao {

    Optional<Subject> findById(int id) throws DaoException;
    void add(Subject subject) throws DaoException;


}
