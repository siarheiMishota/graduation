package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.FilePath;
import by.mishota.graduation.resource.PagePathManager;
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
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.CONTROLLER_MAIN_GET;
import static java.lang.Math.ceil;

public class ViewFacultiesGetCommand implements ActionCommand {
    private static Logger logger = LogManager.getLogger();

    private final int numberPageDefault = 1;
    private int numberRowsOnPage = 5;
    private FacultyService facultyService;
    private SubjectService subjectService;

    public ViewFacultiesGetCommand(FacultyService facultyService, SubjectService subjectService) {
        this.facultyService = facultyService;
        this.subjectService = subjectService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(FilePath.PATH_PAGE_VIEW_FACULTIES));

        HttpSession session = request.getSession();
        String stringNumberPage = request.getParameter(ATTRIBUTE_PAGE);
        int numberOfPage;
        int numberOfPages;

        String message = (String) session.getAttribute(ATTRIBUTE_MESSAGE);
        session.removeAttribute(ATTRIBUTE_MESSAGE);
        request.setAttribute(ATTRIBUTE_MESSAGE, message);

        if (UtilValidator.positiveNumberValidate(stringNumberPage)) {
            numberOfPage = Integer.parseInt(stringNumberPage);
        } else {
            numberOfPage = numberPageDefault;
        }

        int numberSkippedRows = (numberOfPage - 1) * numberRowsOnPage;

        try {
            int numberRows = facultyService.numberFaculties();
            numberOfPages = (int) ceil(1.0 * numberRows / numberRowsOnPage);
            if (numberOfPage > numberOfPages) {
                router.setPage(CONTROLLER_MAIN_GET + "&&page=" + numberOfPages);
                router.setRedirect();
                return router;
            }
            List<Faculty> faculties = facultyService.findFewSkippingFew(numberRowsOnPage, numberSkippedRows);
            Map<Integer, List<String>> nameNeedSubjects = new HashMap<>();



            for (Faculty faculty : faculties) {
                List<String> subjects = subjectService.findAllByFacultyId(faculty.getId())
                        .stream()
                        .map(Subject::getName)
                        .collect(Collectors.toList());
                nameNeedSubjects.put(faculty.getId(), subjects);
            }
            request.setAttribute(ATTRIBUTE_NUMBER_OF_PAGE, numberOfPage);
            request.setAttribute(ATTRIBUTE_NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(ATTRIBUTE_FACULTIES, faculties);
            request.setAttribute(ATTRIBUTE_SUBJECTS, nameNeedSubjects);
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check login for forgotten password is error", e);
        }

        return router;

    }
}
