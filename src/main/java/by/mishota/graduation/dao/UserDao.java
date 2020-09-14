package by.mishota.graduation.dao;

import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserDao extends Dao {
    List<User> findAll() throws DaoException;

    Optional<User> findById(int id) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    List<User> findAllMales() throws DaoException;

    List<User> findAllFemales() throws DaoException;

    List<User> findAllAdults() throws DaoException;

    Optional<User> findByActivationCode(String activationCode) throws DaoException;

    int findCountByEmail(String email) throws DaoException;

    int findCountByLogin(String login) throws DaoException;

    int findCountByPassportId(String passportId) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    Optional<User> add(User user) throws DaoException, DuplicateException;

    Optional<User> add(User user, Connection connection) throws DaoException, DuplicateException;

    int update(User user) throws DaoException, DuplicateException;

    boolean deleteById(int id) throws DaoException;

    boolean deleteById(int id, Connection connection) throws DaoException;

    boolean deleteByLogin(String login) throws DaoException;

    boolean deleteByLogin(String login, Connection connection) throws DaoException;

    int update(User user, Connection connection) throws DaoException, DuplicateException;
}