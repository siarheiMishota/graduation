package by.mishota.graduation.controller.command.impl.admin;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.*;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.EntrantService;
import by.mishota.graduation.service.FacultyService;
import by.mishota.graduation.service.SubjectService;
import by.mishota.graduation.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.Attribute.SERVER_IS_TEMPORARILY_UNAVAILABLE;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.CONTROLLER_MAIN_GET;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ADMIN_VIEW_ENTRANTS;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ADMIN_VIEW_PROFILE_ENTRANT;
import static java.lang.Math.ceil;

public class ViewProfileEntrantGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private SubjectService subjectService;
    private EntrantService entrantService;
    private FacultyService facultyService;

    public ViewProfileEntrantGetCommand(SubjectService subjectService, EntrantService entrantService, FacultyService facultyService) {
        this.subjectService = subjectService;
        this.entrantService = entrantService;
        this.facultyService = facultyService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_ADMIN_VIEW_PROFILE_ENTRANT));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        int entrantId;
        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            router.setPage(CONTROLLER_LOGIN_GET);
            router.setRedirect();
            return router;
        }

        if (user.getRole() != Role.ADMIN){
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_ACCESS);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }


        String entrantIdString = request.getParameter(ATTRIBUTE_ENTRANT_ID);

        if (UtilValidator.positiveNumberValidate(entrantIdString)) {
            entrantId = Integer.parseInt(entrantIdString);
        } else {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_INCORRECT_ENTRANT_ID);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }

        try {

            Optional<Entrant> entrantOptional = entrantService.findById(entrantId);
            if (entrantOptional.isEmpty()){
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_INCORRECT_ENTRANT_ID);
                router.setPage(CONTROLLER_MAIN_GET);
                router.setRedirect();
                return router;
            }

            Entrant entrant=entrantOptional.get();
            Map<String,String> stringIdAndNameSubjectsMap=new HashMap<>();
            Map<String,String> stringIdAndNameFacultiesMap=new HashMap<>();

            Map<Integer, String> idAndNameSubjectsMap = subjectService.findAll()
                    .stream()
                    .collect(Collectors.toMap(Subject::getId, Subject::getName));

            Map<Integer, String> idAndNameFacultiesMap = facultyService.findAll()
                    .stream()
                    .collect(Collectors.toMap(Faculty::getId, Faculty::getName));

            for (Map.Entry<Integer,String> entry:idAndNameSubjectsMap.entrySet()) {
                stringIdAndNameSubjectsMap.put(Integer.toString(entry.getKey()),entry.getValue());
            }

            for (Map.Entry<Integer,String> entry:idAndNameFacultiesMap.entrySet()) {
                stringIdAndNameFacultiesMap.put(Integer.toString(entry.getKey()),entry.getValue());
            }

            request.setAttribute(ATTRIBUTE_SUBJECT_NAMES,stringIdAndNameSubjectsMap);
            request.setAttribute(ATTRIBUTE_FACULTY_NAMES, stringIdAndNameFacultiesMap);
            request.setAttribute(ATTRIBUTE_ENTRANT, entrant);
            request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
            session.removeAttribute(ATTRIBUTE_MESSAGE);
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("View entrant profile is error", e);
        }

        router.setPage(CONTROLLER_MAIN_GET);
        return router;

    }
}
