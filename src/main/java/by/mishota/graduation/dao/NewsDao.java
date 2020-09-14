package by.mishota.graduation.dao;

import by.mishota.graduation.entity.News;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface NewsDao extends Dao {
    boolean addAll(List<News> news) throws DaoException, DuplicateException;

    boolean addAll(List<News> news, Connection connection) throws DaoException, DuplicateException;

    boolean add(News news) throws DaoException, DuplicateException;

    boolean add(News news, Connection connection) throws DaoException, DuplicateException;

    int update(News news, Connection connection) throws DaoException, DuplicateException;

    int update(News news) throws DaoException, DuplicateException;

    boolean delete(int id) throws DaoException;

    boolean delete(int id, Connection connection) throws DaoException;

    boolean deleteSome(List<Integer> listId) throws DaoException;

    List<News> findAll() throws DaoException;

    Optional<News> findById(int id) throws DaoException;

    List<News> findFewSkippingFew(int numberFind, int numberSkipping) throws DaoException;

    int numberNews() throws DaoException;
}
