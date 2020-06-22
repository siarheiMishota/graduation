package by.mishota.graduation.service.impl;

import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.impl.UserDaoImpl;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.ParamStringService;
import by.mishota.graduation.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Override
    public boolean checkUser(String login, String password) throws ServiceException {
        UserDao userDao=new UserDaoImpl();
        try {
            Optional<User> foundUser = userDao.findByLogin(login);
            if (foundUser.isPresent()){
                return foundUser.get().getPassword().equals(password);
            }
        } catch (DaoException e) {
            System.out.println("AAAAAAAAA");
            System.out.println(e);
            throw new ServiceException(ParamStringService.ERROR_GETTING_THE_USER,e);

        }
        return false;
    }

    @Override
    public boolean checkAdmin(String login, String password) throws ServiceException {
        return false;
    }
}
