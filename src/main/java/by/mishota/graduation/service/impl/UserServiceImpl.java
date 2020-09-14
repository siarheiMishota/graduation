package by.mishota.graduation.service.impl;

import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.controller.command.impl.UrlDomain;
import by.mishota.graduation.dao.PhotoDao;
import by.mishota.graduation.dao.UserDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.MailException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.mail.MailSender;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.validation.UserValidator;
import com.mysql.cj.PreparedQuery;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.util.Md5Util.generateHashMd5;

public class UserServiceImpl implements UserService {

    public static final String MESSAGE_ACTIVATION_CODE = "Hello, %s!\n" +
            "Welcome to  admission committee. Please, visit next link:\n\n " +
            "<a href=\"%s/controller?command=activation&activationCode=%s\"> Activation code</a> ";
    public static final String MESSAGE_FORGOTTEN_CODE = "Hello, %s!\n" +
            "Welcome to  admission committee. Please, visit next link:\n\n " +
            "<a href=\"%s%s&activationCode=%s\"> Recovering code</a> ";

    public UserServiceImpl() {
    }

    @Override
    public boolean checkSignIn(String login, String password) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            Optional<User> foundUserOptional = userDao.findByLogin(login);
            if (foundUserOptional.isPresent()) {
                if (foundUserOptional.get().isConfirmed()) {
                    String s = generateHashMd5(password);
                    return foundUserOptional.get().getPassword().equals(s);
                }
            }
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user", e);

        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Encryption error of password", e);

        }
        return false;
    }

    @Override
    public Map<String, String> checkEmailForForgottenPassword(String email) throws ServiceException {

        Map<String, String> validateResultMap = UserValidator.validateLogin(email);

        if (!validateResultMap.isEmpty()) {
            return validateResultMap;
        }

        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            Optional<User> foundUser = userDao.findByEmail(email);
            if (foundUser.isPresent()) {
                User user = foundUser.get();

                if (user.getActivationCode() != null || !user.isConfirmed()) {
                    validateResultMap.put(ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_NOT_ACTIVATED);
                    return validateResultMap;
                }


            } else {
                validateResultMap.put(ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_NOT_EXIST);
            }
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user by email", e);

        }
        return validateResultMap;
    }

    @Override
    public Map<String, String> checkSignUp(String login, String email, String password, String surname,
                                           String firstName, String fatherName, String passportId,
                                           String birth, String gender) throws ServiceException {

        Map<String, String> validatingResult = UserValidator.validateSignUp(login, email, password, surname, firstName, fatherName, passportId, birth, gender);

        if (!validatingResult.containsKey(ATTRIBUTE_LOGIN) && !validateUniqueLoginBd(login)) {
            validatingResult.put(ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_LOGIN);
        }

        if (!validatingResult.containsKey(ATTRIBUTE_EMAIL) && !validateUniqueEmailBd(email)) {
            validatingResult.put(ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_EMAIL);
        }

        if (!validatingResult.containsKey(ATTRIBUTE_PASSPORT_ID) && !validateUniquePassportIdBd(passportId)) {
            validatingResult.put(ATTRIBUTE_PASSPORT_ID, VALUE_ATTRIBUTE_PASSPORT_ID);
        }
        return validatingResult;
    }

    @Override
    public Map<String, String> checkUpdateProfile(User user, String login, String email, String password, String surname, String firstName, String fatherName, String passportId, String birth, String gender) throws ServiceException {
        Map<String, String> validatingResult = UserValidator.validateUpdateProfile(login, email, surname, firstName, fatherName, passportId, birth, gender);

        if (user == null) {
            throw new ServiceException("User is null when checking updating him profile");
        }
        if (!validatingResult.containsKey(ATTRIBUTE_LOGIN)
                && !user.getLogin().equals(login)
                && !validateUniqueLoginBd(login)) {
            validatingResult.put(ATTRIBUTE_LOGIN, VALUE_ATTRIBUTE_LOGIN);
        }

        if (!validatingResult.containsKey(ATTRIBUTE_EMAIL)
                && !user.getEmail().equals(email)
                && !validateUniqueEmailBd(email)) {
            validatingResult.put(ATTRIBUTE_EMAIL, VALUE_ATTRIBUTE_EMAIL);
        }

        if (!validatingResult.containsKey(ATTRIBUTE_PASSPORT_ID)
                && !user.getPassportId().equals(passportId)
                && !validateUniquePassportIdBd(passportId)) {
            validatingResult.put(ATTRIBUTE_PASSPORT_ID, VALUE_ATTRIBUTE_PASSPORT_ID);
        }
        return validatingResult;
    }

    @Override
    public Map<String, String> checkPassword(String password) {
        return UserValidator.validatePassword(password);
    }

    @Override
    public Map<String, String> checkExtensionPhoto(String fileName) {
        return UserValidator.validateExtensionPhoto(fileName);
    }


    @Override
    public Optional<User> add(User user) throws ServiceException, DuplicateException {
        Optional<User> added;
        MailSender mailSender = new MailSender();
        try {

            user.setActivationCode(UUID.randomUUID().toString());

            added = DaoFactory.getInstance().getUserDao().add(user);

            if (added.isPresent()) {

                String message = String.format(MESSAGE_ACTIVATION_CODE,
                        added.get().getLogin(),
                        UrlDomain.PATH,
                        added.get().getActivationCode());
                mailSender.sendConfirmationLink(user.getEmail(), SUBJECT_ACTIVATION_CODE, message);
            }
        } catch (DaoException | MailException e) {
            throw new ServiceException(e);
        }
        return added;
    }

    @Override
    public int update(User user) throws ServiceException, DuplicateException {
        try {
            boolean newLogin=false;
            MailSender mailSender = new MailSender();
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            Optional<User> optionalUserById = userDao.findById(user.getId());

            if (optionalUserById.isEmpty()){
                return -1;
            }

            User userFromDb=optionalUserById.get();
            if (!userFromDb.getEmail().equals(user.getEmail())){
                user.setConfirmed(false);
                user.setActivationCode(UUID.randomUUID().toString());
                newLogin=true;
            }

            int updating = userDao.update(user);

            if (updating==1){
                if (newLogin){
                    String message = String.format(MESSAGE_ACTIVATION_CODE,
                            user.getLogin(),
                            UrlDomain.PATH,
                            user.getActivationCode());
                    mailSender.sendConfirmationLink(user.getEmail(), SUBJECT_ACTIVATION_CODE, message);
                }
                return 1;
            }
            return 0;
        } catch (DaoException |MailException e) {
            throw new ServiceException("Error updating the user", e);
        }
    }

    @Override
    public boolean addPhoto(User user, String name) throws ServiceException, DuplicateException {
        try {
            if (DaoFactory.getInstance().getPhotoDao().add(user, name)) {
                List<String> userPhotos = user.getPhotos();
                if (userPhotos == null) {
                    userPhotos = new ArrayList<>();
                }
                userPhotos.add(name);
                user.setPhotos(userPhotos);
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addPhotos(User user, List<String> names) throws ServiceException, DuplicateException {
        try {
            if (DaoFactory.getInstance().getPhotoDao().addAll(user, names)) {
                List<String> userPhotos = user.getPhotos();
                if (userPhotos == null) {
                    userPhotos = new ArrayList<>();
                }
                userPhotos.addAll(names);
                user.setPhotos(userPhotos);
            }
            return false;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deletePhotos(List<String> names) throws ServiceException {
        try {
            return DaoFactory.getInstance().getPhotoDao().deleteSome(names);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean confirmEmail(String activationCode) throws ServiceException, DuplicateException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        int updatedRows;
        try {
            Optional<User> userByActivationCode = userDao.findByActivationCode(activationCode);

            if (userByActivationCode.isEmpty()) {
                return false;
            }

            userByActivationCode.get().setActivationCode(null);
            userByActivationCode.get().setConfirmed(true);

            updatedRows = userDao.update(userByActivationCode.get());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return updatedRows == 1;
    }

    @Override
    public boolean sendRecoveringForgottenPassword(User user) throws ServiceException, DuplicateException {

        MailSender mailSender = new MailSender();
        try {

            user.setActivationCode(UUID.randomUUID().toString());

            int numberUpdatedRows = DaoFactory.getInstance().getUserDao().update(user);

            if (numberUpdatedRows != 0) {
                String message = String.format(MESSAGE_FORGOTTEN_CODE,
                        user.getLogin(),
                        UrlDomain.PATH,
                        ParamStringCommand.CONTROLLER_ACTIVATION_FORGOTTEN_PASSWORD_GET,
                        user.getActivationCode());
                mailSender.sendConfirmationLink(user.getEmail(), SUBJECT_ACTIVATION_CODE, message);
                return true;
            }
        } catch (DaoException | MailException e) {
            throw new ServiceException(e);
        }
        return false;
    }

    @Override
    public List<String> findAllPhotosByUser(User user) throws ServiceException {
        PhotoDao photoDao = DaoFactory.getInstance().getPhotoDao();
        try {
            return photoDao.findAllByUserId(user.getId());
        } catch (DaoException e) {
            throw new ServiceException("Error getting photos by user", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user", e);
        }
    }

    @Override
    public Optional<User> findUserByActivationCode(String activationCode) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findByActivationCode(activationCode);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user by activation code", e);
        }
    }

    @Override
    public Optional<User> findById(int id) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user by id", e);
        }
    }

    @Override
    public int findCountByPassportId(String passportId) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findCountByPassportId(passportId);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the number users by passport id", e);
        }
    }

    @Override
    public int findCountByLogin(String login) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findCountByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the number users by login", e);
        }
    }

    @Override
    public int findCountByEmail(String email) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findCountByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the number users by email", e);
        }
    }

    @Override
    public Optional<User> findByActivationCode(String activationCode) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findByActivationCode(activationCode);
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user by activation code", e);
        }
    }

    @Override
    public List<User> findAllAdults() throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findAllAdults();
        } catch (DaoException e) {
            throw new ServiceException("Error getting adults users", e);
        }
    }

    @Override
    public List<User> findAllFemales() throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findAllFemales();
        } catch (DaoException e) {
            throw new ServiceException("Error getting female users", e);
        }
    }

    @Override
    public List<User> findAllMales() throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findAllMales();
        } catch (DaoException e) {
            throw new ServiceException("Error getting male users", e);
        }
    }

    private static boolean validateUniqueLoginBd(String login) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            int countUsers = userDao.findCountByLogin(login);
            if (countUsers == 0) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user", e);
        }
        return false;
    }

    private static boolean validateUniqueEmailBd(String email) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            int countUsers = userDao.findCountByEmail(email);
            if (countUsers == 0) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user", e);
        }
        return false;
    }

    private static boolean validateUniquePassportIdBd(String passportId) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            int countUsers = userDao.findCountByPassportId(passportId);
            if (countUsers == 0) {
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException("Error getting the user", e);
        }
        return false;

    }
}
