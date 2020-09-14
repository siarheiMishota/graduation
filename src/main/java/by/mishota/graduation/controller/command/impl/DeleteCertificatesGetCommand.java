package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.*;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_DELETE_CERTIFICATES;

public class DeleteCertificatesGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private SubjectService subjectService;

    public DeleteCertificatesGetCommand(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_DELETE_CERTIFICATES));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant = (Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_DELETE_CERTIFICATES_GET);
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
            if (entrant.getResults().isEmpty()) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_ENTRANT_NULL);
                router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
                router.setRedirect();
                return router;
            }

            List<Subject> entrantSubjects = new ArrayList<>();
            for (SubjectResult subjectResult : entrant.getResults()) {
                Optional<Subject> subjectOptional = subjectService.findById(subjectResult.getSubjectId());
                subjectOptional.ifPresent(entrantSubjects::add);
            }

            request.setAttribute(ATTRIBUTE_MESSAGE, session.getAttribute(ATTRIBUTE_MESSAGE));
            session.removeAttribute(ATTRIBUTE_MESSAGE);
            request.setAttribute(ATTRIBUTE_SUBJECTS, entrantSubjects);
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
        }

        router.setPage(CONTROLLER_MAIN_GET);
        return router;
    }
}
