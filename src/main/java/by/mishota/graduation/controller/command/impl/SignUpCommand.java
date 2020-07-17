package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.util.Md5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static by.mishota.graduation.resource.FilePath.*;

public class SignUpCommand implements ActionCommand {

    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    private static final String SERVER_IS_TEMPORARILY_UNAVAILABLE = "Server is temporarily unavailable";

    private static final Logger LOGGER = LogManager.getLogger();
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String SURNAME = "surname";
    public static final String FIRST_NAME = "firstName";
    public static final String FATHER_NAME = "fatherName";
    public static final String PASSPORT_ID = "passportId";
    public static final String BIRTH = "birth";
    public static final String GENDER = "gender";
    public static final String ERRORS = "errors";

    private UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);

        String login = request.getParameter(LOGIN);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String surname = request.getParameter(SURNAME);
        String firstName = request.getParameter(FIRST_NAME);
        String fatherName = request.getParameter(FATHER_NAME);
        String passportId = request.getParameter(PASSPORT_ID);
        String birthString = request.getParameter(BIRTH);
        String genderString = request.getParameter(GENDER);

        try {
            Map<String, String> incorrectUserMap = userService.checkSignUp(login, email, password, surname, firstName,
                    fatherName, passportId, birthString, genderString, "");//todo

            if (!incorrectUserMap.isEmpty()) {
                page = ConfigurationManager.getProperty(PATH_PAGE_SIGN_UP);
                settingAttributesInRequestForDefault(request, login, email, surname, firstName, fatherName, passportId, birthString, genderString, incorrectUserMap);
                return page;
            }

            LocalDate birth = LocalDate.parse(birthString, DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD));
            Gender gender = Gender.valueOfIgnoreCase(genderString);
            String passwordMd5 = Md5Util.generateHashMd5(password);

            User user = new User.Builder()
                    .setLogin(login)
                    .setEmail(email)
                    .setPassword(passwordMd5)
                    .setSurname(surname)
                    .setFirstName(firstName)
                    .setFatherName(fatherName)
                    .setPassportId(passportId)
                    .setBirth(birth)
                    .setGender(gender)
                    .build();

            Optional<User> add = userService.add(user);

            if (add.isPresent()) {
                page = ConfigurationManager.getProperty(PATH_PAGE_SIGN_UP_SUCCESSFULLY);
                request.setAttribute(LOGIN, add.get().getLogin());
            } else {
                incorrectUserMap = userService.checkSignUp(login, email, password, surname, firstName,
                        fatherName, passportId, birthString, genderString, "");//todo

                if (!incorrectUserMap.isEmpty()) {
                    page = ConfigurationManager.getProperty(PATH_PAGE_SIGN_UP);
                    settingAttributesInRequestForDefault(request, login, email, surname, firstName, fatherName, passportId, birthString, genderString, incorrectUserMap);
                    return page;
                }
            }


        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            LOGGER.info("sign up error", e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.info("md5 isn't worked");
        }

        return page;
    }

    private void settingAttributesInRequestForDefault(HttpServletRequest request, String login, String email, String surname, String firstName, String fatherName, String passportId, String birthString, String genderString, Map<String, String> incorrectUserMap) {
        request.setAttribute(LOGIN, login);
        request.setAttribute(EMAIL, email);
        request.setAttribute(SURNAME, surname);
        request.setAttribute(FIRST_NAME, firstName);
        request.setAttribute(FATHER_NAME, fatherName);
        request.setAttribute(PASSPORT_ID, passportId);
        request.setAttribute(BIRTH, birthString);
        request.setAttribute(GENDER, genderString);
        request.setAttribute(ERRORS, incorrectUserMap);
    }
}
