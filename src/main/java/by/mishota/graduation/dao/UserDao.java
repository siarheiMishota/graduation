package by.mishota.graduation.dao;

import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.UserDaoException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll() throws UserDaoException;
    Optional<User> findById(int id) throws UserDaoException;
    List<User> findAllMales() throws UserDaoException;
    List<User> findAllFemales() throws UserDaoException;
    List<User> findAllAdults() throws UserDaoException;

}