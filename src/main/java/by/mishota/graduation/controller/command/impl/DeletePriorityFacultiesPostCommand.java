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
import by.mishota.graduation.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class DeletePriorityFacultiesPostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private EntrantService entrantService;

    public DeletePriorityFacultiesPostCommand(EntrantService entrantService) {
        this.entrantService = entrantService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_DELETE_PRIORITY_FACULTIES_GET);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant = (Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_DELETE_PRIORITY_FACULTIES_GET);
            router.setPage(CONTROLLER_LOGIN_GET);
            router.setRedirect();
            return router;
        }

        if (user.getRole()== Role.ADMIN){
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_ACCESS);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }

        if (entrant == null || entrant.getPriorities().isEmpty()) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_ENTRANT_NULL);
            router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
            router.setRedirect();
            return router;
        }

        try {
            String[] prioritiesString = request.getParameterValues(ATTRIBUTE_PRIORITY);

            if (prioritiesString == null) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_SELECTED);
                router.setPage(CONTROLLER_DELETE_PRIORITY_FACULTIES_GET);
                router.setRedirect();
                return router;
            }

            for (String priorityString : prioritiesString) {
                if (!UtilValidator.positiveNumberValidate(priorityString)) {
                    session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_NUMBER);
                    router.setPage(CONTROLLER_DELETE_PRIORITY_FACULTIES_GET);
                    router.setRedirect();
                    return router;
                }
            }

            List<Integer> priorities = Arrays.stream(prioritiesString).map(Integer::parseInt).collect(Collectors.toList());
            List<FacultyPriority> facultyPriorities = new ArrayList<>();

            for (FacultyPriority facultyPriority : entrant.getPriorities()) {
                if (priorities.contains(facultyPriority.getPriority())) {
                    facultyPriorities.add(facultyPriority);
                }
            }
            boolean result = entrantService.deletePriorities(entrant, facultyPriorities);



            Optional<Entrant> entrantOptional = entrantService.findById(entrant.getId());

            if (entrantOptional.isPresent()) {
                entrant = entrantOptional.get();
            }

            if (result) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DELETE_PRIORITY_FACULTIES_SUCCESS);
            } else {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DELETE_PRIORITY_FACULTIES_ERROR);
            }
            session.setAttribute(ATTRIBUTE_ENTRANT, entrant);
            router.setRedirect();
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
        } catch (DuplicateException e) {
            logger.warn("Deleting priorities of faculties are error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        router.setPage(CONTROLLER_MAIN_GET);
        return router;
    }
}
