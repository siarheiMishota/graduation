package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.*;
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

import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.Attribute.*;

public class DeleteCertificatesPostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private EntrantService entrantService;

    public DeleteCertificatesPostCommand(EntrantService entrantService) {
        this.entrantService = entrantService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_DELETE_CERTIFICATES_GET);
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

        if (entrant == null || entrant.getResults()==null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_ENTRANT_NULL);
            router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
            router.setRedirect();
            return router;
        }

        try {
            String[] subjectsIdString = request.getParameterValues(ATTRIBUTE_SUBJECT );

            if (subjectsIdString==null){
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_SELECTED);
                router.setRedirect();
                return router;
            }

            for (String  subjectIdString : subjectsIdString) {
                if (!UtilValidator.positiveNumberValidate(subjectIdString)) {
                    session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_NUMBER);
                    router.setRedirect();
                    return router;
                }
            }

            List<Integer> subjectsIdForDeleting = Arrays.stream(subjectsIdString).map(Integer::parseInt).collect(Collectors.toList());
            List<SubjectResult> subjectsResultForDeleting = new ArrayList<>();

            for (SubjectResult subjectResult : entrant.getResults()) {
                if (subjectsIdForDeleting.contains(subjectResult.getSubjectId())){
                    subjectsResultForDeleting.add(subjectResult);
                }
            }

            boolean result = entrantService.deleteResults(entrant, subjectsResultForDeleting);

            if (result) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DELETE_CERTIFICATES_SUCCESS);
            } else {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DELETE_CERTIFICATES_ERROR);
            }

            Optional<Entrant> entrantOptional = entrantService.findById(entrant.getId());

            if(entrantOptional.isPresent()){
                entrant=entrantOptional.get();
            }

            if (entrant.getResults().isEmpty()){
                router.setPage(CONTROLLER_MAIN_GET);
            }

            session.setAttribute(ATTRIBUTE_ENTRANT,entrant);
            session.setAttribute(ATTRIBUTE_SUM_CERTIFICATES,entrantService.sumCertificates(entrant));
            router.setRedirect();
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
        } catch (DuplicateException e) {
            logger.warn("Deleting certificates are error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        router.setPage(CONTROLLER_MAIN_GET);
        return router;
    }
}
