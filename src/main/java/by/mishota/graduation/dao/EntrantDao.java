package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface EntrantDao extends Dao {

    Optional<Entrant> findById(int id) throws DaoException;

    List<Entrant> findAllByFaculty(int idFaculty) throws DaoException;

    int numberEntrantMoreSpecify(int numberSpecify);

    int numberEntrantEqualsSpecify(int numberSpecify);

    Optional<Entrant> add(Entrant entrant) throws DaoException;

    Optional<Entrant> add(Entrant entrant, Connection connection) throws DaoException;

    int update(Entrant entrant) throws DaoException;
    int update(Entrant entrant,Connection connection) throws DaoException;

}
