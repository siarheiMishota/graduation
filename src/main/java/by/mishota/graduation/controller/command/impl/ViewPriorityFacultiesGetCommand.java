package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.EntrantService;
import by.mishota.graduation.service.FacultyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_VIEW_PRIORITY_FACULTIES;

public class ViewPriorityFacultiesGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private FacultyService facultyService;
    private EntrantService entrantService;

    public ViewPriorityFacultiesGetCommand(FacultyService facultyService, EntrantService entrantService) {
        this.facultyService = facultyService;
        this.entrantService = entrantService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_VIEW_PRIORITY_FACULTIES));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant=(Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_VIEW_PRIORITY_FACULTIES_GET);
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

        if (entrant == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_ENTRANT_NULL);
            router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
            router.setRedirect();
            return router;
        }

        try {

            Optional<Entrant> entrantOptional = entrantService.findByUserId(user.getId());
            if (entrantOptional.isEmpty()) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_ENTRANT_NULL);
                router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
                router.setRedirect();
                return router;
            }

             entrant = entrantOptional.get();

            if (entrant.getPriorities().isEmpty()) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PRIORITIES_EMPTY);
                router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
                router.setRedirect();
                return router;
            }
            request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
            session.removeAttribute(ATTRIBUTE_MESSAGE);

            Map<Integer, String> priorityFacultiesMap = new HashMap<>();

            for (FacultyPriority facultyPriority : entrant.getPriorities()) {
                String facultyName = facultyService.findById(facultyPriority.getFacultyId()).get().getName();
                priorityFacultiesMap.put(facultyPriority.getPriority(), facultyName);
            }


            request.setAttribute(ATTRIBUTE_FACULTIES, priorityFacultiesMap);
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
        }

        router.setPage(CONTROLLER_MAIN_GET);
        return router;
    }
}
