package by.mishota.graduation.service.impl;

import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.impl.UserDaoImpl;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.MailException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.mail.MailSender;
import by.mishota.graduation.service.ParamStringService;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.validation.Validator;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static by.mishota.graduation.util.Md5.generateHashMd5;

public class UserServiceImpl implements UserService {

    private MailSender mailSender;

    public UserServiceImpl() {
        mailSender = new MailSender();
    }

    @Override
    public boolean checkSignIn(String login, String password) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try {
            Optional<User> foundUser = userDao.findByLogin(login);
            if (foundUser.isPresent()) {
                String s = generateHashMd5(password);
                return foundUser.get().getPassword().equals(s);
            }
        } catch (DaoException e) {
            throw new ServiceException(ParamStringService.ERROR_GETTING_THE_USER, e);

        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Encryption error of password", e);

        }
        return false;
    }

    @Override
    public Role checkRole(String login, String password) throws ServiceException {

        if (login == null || password == null) {
            return Role.GUEST;
        }

        UserDao userDao = new UserDaoImpl();
        try {
            Optional<User> foundUser = userDao.findByLogin(login);
            if (foundUser.isPresent()) {
                String s = generateHashMd5(password);
                if (foundUser.get().getPassword().equals(s)) {
                    return foundUser.get().getRole();
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(ParamStringService.ERROR_GETTING_THE_USER, e);

        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Encryption error of password", e);

        }

        return Role.GUEST;
    }

    @Override
    public Map<String, String> checkSignUp(String login, String email, String password, String surname,
                                           String firstName, String fatherName, String passportId,
                                           String birth, String gender, String namePhoto) throws ServiceException {

        Map<String, String> validatingResult = Validator.validateSignUp(email, password, birth, gender, namePhoto);

        if (!validateUniqueLoginBd(login)) {
            validatingResult.put("login", "loginIsTaken");
        }

        if (!validateUniqueEmailBd(email)) {
            validatingResult.put("email", "emailIsTaken");
        }

        if (!validateUniquePassportIdBd(passportId)) {
            validatingResult.put("passportId", "passportIdIsTaken");
        }

        return validatingResult;


    }

    @Override
    public Optional<User> add(User user) throws ServiceException {
        Optional<User> added;
        try {

            user.setActivationCode(UUID.randomUUID().toString());

            added = new UserDaoImpl().add(user);

            if (added.isPresent()) {

                String message = String.format("Hello, %s!\n" +
                                "Welcome to  admission committee. Please, visit next link:\n\n " +
                                "<a href=\"http://localhost:8080/controller?command=activation&activationCode=%s\"> Activation code</a> ",
                        added.get().getLogin(),
                        added.get().getActivationCode());
                mailSender.sendConfirmationLink(user.getEmail(), "Activation code", message);
            }
        } catch (DaoException | MailException e) {
            if ("Cannot insert a duplicate user ".equalsIgnoreCase(e.getMessage())) {
                return Optional.empty();
            }

            throw new ServiceException(e);
        }
        return added;
    }

    @Override
    public boolean confirmEmail(String activationCode) throws ServiceException {
        UserDao dao=new UserDaoImpl();
        int updatedRows;
        try {
            Optional<User> userByActivationCode = dao.findByActivationCode(activationCode);

            if (userByActivationCode.isEmpty()){
                return false;
            }

            userByActivationCode.get().setActivationCode(null);
            userByActivationCode.get().setConfirmed(true);

            updatedRows = dao.update(userByActivationCode.get());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return updatedRows==1;
    }


    private static boolean validateUniqueLoginBd(String login) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try {
            int countUsers = userDao.findCountByLogin(login);
            if (countUsers == 0) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(ParamStringService.ERROR_GETTING_THE_USER, e);
        }
        return false;
    }

    private static boolean validateUniqueEmailBd(String email) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try {
            int countUsers = userDao.findCountByEmail(email);
            if (countUsers == 0) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(ParamStringService.ERROR_GETTING_THE_USER, e);
        }
        return false;
    }

    private static boolean validateUniquePassportIdBd(String passportId) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try {
            int countUsers = userDao.findCountByPassportId(passportId);
            if (countUsers == 0) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(ParamStringService.ERROR_GETTING_THE_USER, e);

        }
        return false;

    }

    public static void main(String[] args) throws ServiceException {//todo
        UserService userService = new UserServiceImpl();
        User.Builder builder = new User.Builder();

        builder.setId(1);
        builder.setPassportId("312");
        builder.setBirth(LocalDate.now());
        builder.setLogin("ser");
        builder.setPassword("1234");
        builder.setEmail("soloyoloswag1@mail.ru");
        builder.setFirstName("Siarhei");
        builder.setSurname("Misho");
        builder.setFatherName("Aleksandrovich");
        builder.setGender(Gender.MALE);
        builder.setConfirmed(false);
//        builder.setPathToPhoto(Path.of(resultSet.getString(PARAM_USER_PHOTO)));  //todo

        User user = builder.build();
        Optional<User> add = userService.add(user);

//        System.out.println(add.get());
    }
}
