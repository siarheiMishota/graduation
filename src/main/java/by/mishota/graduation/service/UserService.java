package by.mishota.graduation.service;

import by.mishota.graduation.exception.ServiceException;

public interface UserService {

    boolean checkUser(String login, String password) throws ServiceException;
    boolean checkAdmin(String login, String password) throws ServiceException;
}
