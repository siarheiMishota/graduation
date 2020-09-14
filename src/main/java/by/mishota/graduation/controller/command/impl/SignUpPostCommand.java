package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.controller.command.ParamStringCommand;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.util.Md5Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class SignUpPostCommand implements ActionCommand {

    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    private static final String SERVER_IS_TEMPORARILY_UNAVAILABLE = "Server is temporarily unavailable";

    private static final Logger logger = LogManager.getLogger();
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";


    private UserService userService;

    public SignUpPostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_MAIN_GET);
        HttpSession session = request.getSession();

        String login = request.getParameter(ATTRIBUTE_LOGIN);
        String email = request.getParameter(ATTRIBUTE_EMAIL);
        String password = request.getParameter(ATTRIBUTE_PASSWORD);
        String surname = request.getParameter(ATTRIBUTE_SURNAME);
        String firstName = request.getParameter(ATTRIBUTE_FIRST_NAME);
        String fatherName = request.getParameter(ATTRIBUTE_FATHER_NAME);
        String passportId = request.getParameter(ATTRIBUTE_PASSPORT_ID);
        String birthString = request.getParameter(ATTRIBUTE_BIRTH);
        String genderString = request.getParameter(ATTRIBUTE_GENDER);

        try {
            Map<String, String> incorrectUserMap = userService.checkSignUp(login, email, password, surname, firstName,
                    fatherName, passportId, birthString, genderString);

            if (!incorrectUserMap.isEmpty()) {
                router.setPage(ParamStringCommand.CONTROLLER_SIGN_UP_GET);
                settingAttributesInRequestForReturing(request, login, email, surname, firstName, fatherName, passportId, birthString, genderString, incorrectUserMap);
                return router;
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
            Optional<User> added;
            added = userService.add(user);

            if (added.isPresent()) {
                session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_SIGN_UP_COMPLETED_SUCCESSFULLY);
            } else {
                incorrectUserMap = userService.checkSignUp(login, email, password, surname, firstName,
                        fatherName, passportId, birthString, genderString);

                if (!incorrectUserMap.isEmpty()) {
                    router.setPage(CONTROLLER_SIGN_UP_GET);
                    settingAttributesInRequestForReturing(request, login, email, surname, firstName, fatherName, passportId, birthString, genderString, incorrectUserMap);
                    return router;
                }
            }
            router.setRedirect();
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.info("sign up error", e);
        } catch (NoSuchAlgorithmException e) {
            logger.info("md5 isn't worked");
        } catch (DuplicateException e) {
            logger.warn("Check news is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        return router;
    }

    private void settingAttributesInRequestForReturing(HttpServletRequest request, String login, String email, String surname, String firstName, String fatherName, String passportId, String birthString, String genderString, Map<String, String> incorrectUserMap) {
        request.setAttribute(ATTRIBUTE_LOGIN, login);
        request.setAttribute(ATTRIBUTE_EMAIL, email);
        request.setAttribute(ATTRIBUTE_SURNAME, surname);
        request.setAttribute(ATTRIBUTE_FIRST_NAME, firstName);
        request.setAttribute(ATTRIBUTE_FATHER_NAME, fatherName);
        request.setAttribute(ATTRIBUTE_PASSPORT_ID, passportId);
        request.setAttribute(ATTRIBUTE_BIRTH, birthString);
        request.setAttribute(ATTRIBUTE_GENDER, genderString);
        request.setAttribute(ATTRIBUTE_ERRORS, incorrectUserMap);
    }
}
