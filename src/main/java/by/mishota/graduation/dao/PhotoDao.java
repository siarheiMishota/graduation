package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface PhotoDao extends Dao {
    boolean add(User user,String name) throws DaoException, DuplicateException;

    boolean add(User user,String name, Connection connection) throws DaoException, DuplicateException;

    boolean addAll(User user,List<String> names) throws DaoException, DuplicateException;

    boolean addAll(User user, List<String> names, Connection connection) throws DaoException, DuplicateException;

    int update(int id, String name) throws DaoException, DuplicateException;

    int update(int id, String name, Connection connection) throws DaoException, DuplicateException;

    boolean delete(String name) throws DaoException;

    boolean delete(String name,Connection connection) throws DaoException;

    boolean deleteSome(List<String> names) throws DaoException;

    Optional<String> findById(int id) throws DaoException;

    Optional<Integer> findIdByName(String name) throws DaoException;

    List<String> findAll() throws DaoException;

    List<String> findAllByUserId(int userId) throws DaoException;

}
