package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.EntrantService;
import by.mishota.graduation.service.FacultyService;
import by.mishota.graduation.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class EnterPriorityFacultiesPostCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private FacultyService facultyService;
    private EntrantService entrantService;

    public EnterPriorityFacultiesPostCommand(FacultyService facultyService, EntrantService entrantService) {
        this.facultyService = facultyService;
        this.entrantService = entrantService;
    }

    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router(CONTROLLER_ENTER_PRIORITY_FACULTIES_GET);
        router.setRedirect();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant = (Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            router.setPage(CONTROLLER_LOGIN_GET);
            return router;
        }

        if (user.getRole() == Role.ADMIN) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_ACCESS);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }

        if (entrant == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_ENTRANT_NULL);
            router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
            return router;
        }

        if (entrant.getPriorities().size() >= MAX_NUMBER_OF_PRIORITIES) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PRIORITIES_FILL_IN);
            router.setPage(CONTROLLER_MAIN_GET);
            return router;
        }

        String priorityString = request.getParameter(ATTRIBUTE_PRIORITY);
        String idFacultyString = request.getParameter(ATTRIBUTE_FACULTY);

        if (!facultyService.validateFacultyId(idFacultyString) ||
                !UtilValidator.positiveNumberValidate(priorityString)) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_FACULTY_NULL);
            router.setPage(CONTROLLER_ENTER_PRIORITY_FACULTIES_GET);
            return router;
        }

        int facultyId = Integer.parseInt(idFacultyString);
        int priority = Integer.parseInt(priorityString);

        Map<String, String> incorrectCertificatesMap;

        if (entrant.getPriorities().stream().map(FacultyPriority::getFacultyId).collect(Collectors.toList()).contains(facultyId)) {
            incorrectCertificatesMap = new HashMap<>();
            incorrectCertificatesMap.put(ATTRIBUTE_PRIORITY, VALUE_ATTRIBUTE_DUPLICATE);
            request.setAttribute(ATTRIBUTE_ERRORS, incorrectCertificatesMap);
            router.setPage(CONTROLLER_ENTER_PRIORITY_FACULTIES_GET);
            router.setForward();
            return router;
        }


        entrant.addFacultyPriority(new FacultyPriority(facultyId, priority));

        try {
            entrantService.addPriority(entrant);
            session.setAttribute(ATTRIBUTE_ENTRANT, entrant);

        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Adding entrant is error", e);
        } catch (DuplicateException e) {
            logger.warn("Entering priorities of faculties are error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        return router;
    }
}
