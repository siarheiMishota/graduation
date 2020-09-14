package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.command.impl.SignUpPostCommand.PATTERN_YYYY_MM_DD;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_UPDATE_PROFILE;

public class UpdateProfilePostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private UserService userService;

    public UpdateProfilePostCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_MAIN_GET);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_UPDATE_PROFILE_GET);
            router.setPage(CONTROLLER_LOGIN_GET);
            router.setRedirect();
            return router;
        }

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
            Map<String, String> incorrectUserMap = userService.checkUpdateProfile(user, login, email, password, surname, firstName,
                    fatherName, passportId, birthString, genderString);

            if (!incorrectUserMap.isEmpty()) {
                User returnUser = fillingUser(user, login, email, surname, firstName, fatherName, passportId);

                router.setPage(PagePathManager.getProperty(PATH_PAGE_UPDATE_PROFILE));
                request.setAttribute(ATTRIBUTE_USER, returnUser);
                request.setAttribute(ATTRIBUTE_ERRORS, incorrectUserMap);
                return router;
            }

            LocalDate birth = LocalDate.parse(birthString, DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD));
            Gender gender = Gender.valueOfIgnoreCase(genderString);

            User updatingUser = new User.Builder()
                    .setId(user.getId())
                    .setLogin(login)
                    .setEmail(email)
                    .setPassword(user.getPassword())
                    .setSurname(surname)
                    .setFirstName(firstName)
                    .setFatherName(fatherName)
                    .setPassportId(passportId)
                    .setBirth(birth)
                    .setGender(gender)
                    .setPhotos(user.getPhotos())
                    .setActivationCode(user.getActivationCode())
                    .setConfirmed(user.isConfirmed())
                    .build();
            int updateResult;
                updateResult = userService.update(updatingUser);

            if (updateResult == 1) {
                router.setRedirect();
                if (updatingUser.isConfirmed()) {
                    session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_UPDATE_PROFILE_SUCCESS);
                } else {
                    session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_UPDATE_PROFILE_SUCCESS_AND_CHANGE_EMAIL);
                }
                Optional<User> sessionUser = userService.findById(user.getId());
                sessionUser.ifPresent(value -> session.setAttribute(ATTRIBUTE_USER, value));
                return router;
            }
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.info("sign up error", e);
        } catch (DuplicateException e) {
            logger.warn("Updating profile is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        User returnUser = fillingUser(user, login, email, surname, firstName, fatherName, passportId);
        router.setPage(PagePathManager.getProperty(PATH_PAGE_UPDATE_PROFILE));
        request.setAttribute(ATTRIBUTE_USER, returnUser);
        return router;

    }

    private User fillingUser(User user, String login, String email, String surname, String firstName, String fatherName, String passportId) {
        return new User.Builder()
                .setId(user.getId())
                .setLogin(login)
                .setEmail(email)
                .setSurname(surname)
                .setFirstName(firstName)
                .setFatherName(fatherName)
                .setPassportId(passportId)
                .setBirth(user.getBirth())
                .setGender(user.getGender())
                .build();
    }
}
