package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.*;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.FacultyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ENTER_PRIORITY_FACULTIES;

public class EnterPriorityFacultiesGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private FacultyService facultyService;

    public EnterPriorityFacultiesGetCommand(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public Router execute(HttpServletRequest request) {

        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_ENTER_PRIORITY_FACULTIES));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant = (Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_ENTER_PRIORITY_FACULTIES_GET);
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
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

            if (entrant.getPriorities().size() >= MAX_NUMBER_OF_PRIORITIES) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PRIORITIES_FILL_IN);
                router.setPage(CONTROLLER_MAIN_GET);
                router.setRedirect();
                return router;
            }

            List<Integer> facultiesIdForEntrant = entrant.getPriorities()
                    .stream()
                    .map(FacultyPriority::getFacultyId)
                    .collect(Collectors.toList());

            List<Faculty> facultiesEntrant = new ArrayList<>();
            for (int facultyId : facultiesIdForEntrant) {
                Optional<Faculty> facultyOptional = facultyService.findById(facultyId);
                facultyOptional.ifPresent(facultiesEntrant::add);
            }
            List<Integer> entrantSubjectsId = entrant.getResults()
                    .stream()
                    .map(SubjectResult::getSubjectId)
                    .collect(Collectors.toList());
            List<Faculty> facultiesAllBySubjects = facultyService.findAllByNeedSubjects(entrantSubjectsId);

            if (facultiesAllBySubjects.isEmpty()) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_FACULTIES_NOT_FOUND_BY_SUBJECTS);
                router.setPage(CONTROLLER_MAIN_GET);
                router.setRedirect();
                return router;
            }
            facultiesAllBySubjects.removeAll(facultiesEntrant);

            if (facultiesAllBySubjects.isEmpty()) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_FACULTIES_NOT_FOUND_BY_SUBJECTS_ALREADY);
                router.setPage(CONTROLLER_MAIN_GET);
                router.setRedirect();
                return router;
            }

            OptionalInt maxPriority = entrant.getPriorities().stream()
                    .mapToInt(FacultyPriority::getPriority)
                    .max();
            int priority;
            if (maxPriority.isPresent()) {
                priority = maxPriority.getAsInt() + 1;
            } else {
                priority = 1;
            }

            request.setAttribute(ATTRIBUTE_FACULTIES, facultiesAllBySubjects);
            request.setAttribute(ATTRIBUTE_PRIORITY, priority);
            request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
            session.removeAttribute(ATTRIBUTE_MESSAGE);
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
        }

        router.setPage(CONTROLLER_MAIN_GET);

        return router;
    }
}
