package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.*;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.EntrantService;
import by.mishota.graduation.service.FacultyService;
import by.mishota.graduation.service.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ENTER_CERTIFICATES;

public class EnterCertificatesGetCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();
    private static final int maxNumberOfSubjects=3;

    private SubjectService subjectService;
    private EntrantService entrantService;

    public EnterCertificatesGetCommand(SubjectService subjectService, EntrantService entrantService) {
        this.subjectService = subjectService;
        this.entrantService = entrantService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_ENTER_CERTIFICATES));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant = (Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);
        List<Subject> subjects;

        if (user == null) {
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_ENTER_CERTIFICATES_GET);
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

        try {
            if (entrant == null) {
                Optional<Entrant> entrantOptional = entrantService.findByUserId(user.getId());
                if (entrantOptional.isPresent()) {
                    entrant = entrantOptional.get();
                }
            }

            List<Subject> subjectsEntrant = new ArrayList<>();
            if (entrant != null) {

                if (entrant.getResults().size()>=maxNumberOfSubjects){
                    session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_CERTIFICATES_FILL_IN);
                    router.setPage(CONTROLLER_MAIN_GET);
                    router.setRedirect();
                    return router;
                }

                List<Integer> entrantIddSubjects = entrant.getResults()
                        .stream()
                        .map(SubjectResult::getSubjectId)
                        .collect(Collectors.toList());

                for (int idSubject : entrantIddSubjects) {
                    Optional<Subject> subjectOptional = subjectService.findById(idSubject);
                    subjectOptional.ifPresent(subjectsEntrant::add);
                }
            }

            subjects = subjectService.findAll();
            subjects.removeAll(subjectsEntrant);

            request.setAttribute(ATTRIBUTE_SUBJECTS, subjects);
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
