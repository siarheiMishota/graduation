package by.mishota.graduation.service;

import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> add(User user) throws ServiceException, DuplicateException;

    int update(User user) throws ServiceException, DuplicateException;

    boolean addPhoto(User user, String name) throws ServiceException, DuplicateException;

    boolean addPhotos(User user, List<String> name) throws ServiceException, DuplicateException;

    boolean deletePhotos(List<String> names) throws ServiceException;

    boolean checkSignIn(String login, String password) throws ServiceException;

    Map<String, String> checkSignUp(String login, String email, String password, String surname,
                                    String firstName, String fatherName, String passportId,
                                    String birth, String gender) throws ServiceException;

    Map<String, String> checkUpdateProfile(User user, String login, String email, String password, String surname,
                                           String firstName, String fatherName, String passportId,
                                           String birth, String gender) throws ServiceException;

    Map<String, String> checkEmailForForgottenPassword(String email) throws ServiceException;

    Map<String, String> checkPassword(String password);

    Map<String, String> checkExtensionPhoto(String fileName);

    boolean sendRecoveringForgottenPassword(User user) throws ServiceException, DuplicateException;

    boolean confirmEmail(String activationCode) throws ServiceException, DuplicateException;

    Optional<User> findUserByActivationCode(String activationCode) throws ServiceException;

    List<String> findAllPhotosByUser(User user) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findById(int id) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    List<User> findAllMales() throws ServiceException;

    List<User> findAllFemales() throws ServiceException;

    List<User> findAllAdults() throws ServiceException;

    Optional<User> findByActivationCode(String activationCode) throws ServiceException;

    int findCountByEmail(String email) throws ServiceException;

    int findCountByLogin(String login) throws ServiceException;

    int findCountByPassportId(String passportId) throws ServiceException;
}
