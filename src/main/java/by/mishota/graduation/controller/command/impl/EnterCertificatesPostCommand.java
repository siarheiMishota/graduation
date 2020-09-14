
package by.mishota.graduation.controller.command.impl;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.EntrantService;
import by.mishota.graduation.service.SubjectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class EnterCertificatesPostCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    private SubjectService subjectService;
    private EntrantService entrantService;

    public EnterCertificatesPostCommand(SubjectService subjectService, EntrantService entrantService) {
        this.subjectService = subjectService;
        this.entrantService = entrantService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_ENTER_CERTIFICATES_GET);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant = (Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);
        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            router.setPage(CONTROLLER_LOGIN_GET);
            router.setRedirect();
            return router;
        }

        if (user.getRole() == Role.ADMIN) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_ACCESS);
            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
            return router;
        }

        try {
            if (entrant == null) {
                entrant = new Entrant();
                String certificateString = request.getParameter(ATTRIBUTE_CERTIFICATE);
                Map<String, String> incorrectCertificatesMap = subjectService.checkMark(certificateString);

                if (!incorrectCertificatesMap.isEmpty()) {
                    request.setAttribute(ATTRIBUTE_ERRORS, incorrectCertificatesMap);
                    router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
                    return router;
                }

                if (entrant.getUser() == null) {
                    entrant.setUser(user);
                }

                entrant.setCertificate(Integer.parseInt(certificateString));
                entrantService.add(entrant);

            }

            String markString = request.getParameter(ATTRIBUTE_MARK);
            Map<String, String> incorrectCertificatesMap = subjectService.checkMark(markString);

            if (!incorrectCertificatesMap.isEmpty()) {
                request.setAttribute(ATTRIBUTE_ERRORS, incorrectCertificatesMap);
                router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
                return router;
            }
            int mark = Integer.parseInt(markString);
            int subjectId = Integer.parseInt(request.getParameter(ATTRIBUTE_SUBJECT));

            if (entrant.getResults().stream().map(SubjectResult::getId).collect(Collectors.toList()).contains(subjectId)) {
                incorrectCertificatesMap = new HashMap<>();
                incorrectCertificatesMap.put(ATTRIBUTE_SUBJECT, VALUE_ATTRIBUTE_DUPLICATE);
                request.setAttribute(ATTRIBUTE_ERRORS, incorrectCertificatesMap);
                router.setPage(CONTROLLER_ENTER_CERTIFICATES_GET);
                return router;
            }

            entrant.addResult(new SubjectResult(subjectId, mark));

            entrantService.addResult(entrant);

            session.setAttribute(ATTRIBUTE_ENTRANT, entrant);
            session.setAttribute(ATTRIBUTE_SUM_CERTIFICATES, entrantService.sumCertificates(entrant));
            router.setRedirect();
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Adding entrant is error", e);
        } catch (DuplicateException e) {
            logger.warn("Entering certificates is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        return router;
    }
}
