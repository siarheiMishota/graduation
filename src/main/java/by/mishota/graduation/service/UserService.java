package by.mishota.graduation.service;

import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {

    boolean checkSignIn(String login, String password) throws ServiceException;

    Role checkRole(String login, String password) throws ServiceException;

    Map<String, String> checkSignUp(String login, String email, String password, String surname,
                                    String firstName, String fatherName, String passportId,
                                    String birth, String gender, String namePhoto) throws ServiceException;

    Optional<User> add(User user) throws ServiceException;
}
