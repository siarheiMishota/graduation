package by.mishota.graduation.command.impl;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.entity.Gender;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import static by.mishota.graduation.resource.ParamStringManager.*;

public class SignUpCommand implements ActionCommand {

    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    private static final String SERVER_IS_TEMPORARILY_UNAVAILABLE = "Server is temporarily unavailable";

    private static final Logger LOGGER = LogManager.getLogger();

    private UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);

        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String surname = request.getParameter("surname");
        String firstName = request.getParameter("firstName");
        String fatherName = request.getParameter("fatherName");
        String passportId = request.getParameter("passportId");
        String birthString = request.getParameter("birth");
        String genderString = request.getParameter("gender");

        try {
            Map<String, String> incorrectUserMap = userService.checkSignUp(login, email, password, surname, firstName,
                    fatherName, passportId, birthString, genderString, "");//todo

            if (!incorrectUserMap.isEmpty()) {
                page = ConfigurationManager.getProperty(PATH_PAGE_SIGN_UP);
                settingAttributesInRequest(request, login, email, surname, firstName, fatherName, passportId, birthString, genderString, incorrectUserMap);
                return page;
            }

            LocalDate birth = LocalDate.parse(birthString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Gender gender = Gender.valueOfIgnoreCase(genderString);
            String passwordMd5 = User.generateHashMd5(password);

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
                request.setAttribute("login", add.get().getLogin());
            } else {
                incorrectUserMap = userService.checkSignUp(login, email, password, surname, firstName,
                        fatherName, passportId, birthString, genderString, "");//todo

                if (!incorrectUserMap.isEmpty()) {
                    page = ConfigurationManager.getProperty(PATH_PAGE_SIGN_UP);
                    settingAttributesInRequest(request, login, email, surname, firstName, fatherName, passportId, birthString, genderString, incorrectUserMap);
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

    private void settingAttributesInRequest(HttpServletRequest request, String login, String email, String surname, String firstName, String fatherName, String passportId, String birthString, String genderString, Map<String, String> incorrectUserMap) {
        request.setAttribute("login", login);
        request.setAttribute("email", email);
        request.setAttribute("surname", surname);
        request.setAttribute("firstName", firstName);
        request.setAttribute("fatherName", fatherName);
        request.setAttribute("passportId", passportId);
        request.setAttribute("birth", birthString);
        request.setAttribute("gender", genderString);
        request.setAttribute("errors", incorrectUserMap);
    }
}
