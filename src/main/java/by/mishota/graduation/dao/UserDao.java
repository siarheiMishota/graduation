package by.mishota.graduation.dao;

import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao {
    List<User> findAll() throws DaoException;
    Optional<User> findById(int id) throws DaoException;
    Optional<User> findByLogin(String login) throws DaoException;
    List<User> findAllMales() throws DaoException;
    List<User> findAllFemales() throws DaoException;
    List<User> findAllAdults() throws DaoException;

    Optional<User> add(User user) throws DaoException;
}