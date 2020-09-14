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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.controller.Attribute.*;

public class UpdateCertificatesPostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private EntrantService entrantService;
    private SubjectService subjectService;

    public UpdateCertificatesPostCommand(EntrantService entrantService, SubjectService subjectService) {
        this.entrantService = entrantService;
        this.subjectService = subjectService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_MAIN_GET);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        Entrant entrant=(Entrant) session.getAttribute(ATTRIBUTE_ENTRANT);

        if (user == null) {
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

        List<String> nameParametersContainSubjectFromRequest = findFieldsWithSubject(request);
        String certificateString = request.getParameter(ATTRIBUTE_CERTIFICATE);
        List<String> pointsString = getPointsString(request, nameParametersContainSubjectFromRequest);

        Map<String, String> incorrectCertificatesMap = subjectService.checkMark(certificateString);
        if (incorrectCertificatesMap.isEmpty()) {
            for (String pointString : pointsString) {
                incorrectCertificatesMap = subjectService.checkMark(pointString);
            }
        }

        if (!incorrectCertificatesMap.isEmpty()) {
            request.setAttribute(ATTRIBUTE_ERRORS, incorrectCertificatesMap);
            router.setPage(CONTROLLER_UPDATE_CERTIFICATES_GET);
            return router;
        }

        int certificate = Integer.parseInt(certificateString);
        List<SubjectResult> subjectResultsWithRequest = parseSubjectResults(request, nameParametersContainSubjectFromRequest);

        try {
            List<SubjectResult> results = entrant.getResults();

            for (SubjectResult result : results) {
                for (SubjectResult subjectResultWithRequest : subjectResultsWithRequest) {
                    if (result.getSubjectId() == subjectResultWithRequest.getSubjectId()) {
                        subjectResultWithRequest.setId(result.getId());
                        break;
                    }
                }
            }

            entrant.setResults(subjectResultsWithRequest);
            entrant.setCertificate(certificate);
            entrantService.update(entrant);

            session.setAttribute(ATTRIBUTE_SUM_CERTIFICATES, entrantService.sumCertificates(entrant));
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_UPDATING_SUCCESSFUL);


        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Adding entrant is error", e);
        } catch (DuplicateException e) {
            logger.warn("Updating certificates are error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        return router;
    }

    static List<String> getPointsString(HttpServletRequest request, List<String> nameParametersContainSubjectFromRequest) {
        List<String> pointsString = new ArrayList<>();

        for (String nameParameterContainSubject : nameParametersContainSubjectFromRequest) {
            pointsString.add(request.getParameter(nameParameterContainSubject));
        }
        return pointsString;
    }

    static List<String> findFieldsWithSubject(HttpServletRequest request) {
        Enumeration<String> parameterNamesFromRequest = request.getParameterNames();
        List<String> nameParametersContainSubjectFromRequest = new ArrayList<>();

        while (parameterNamesFromRequest.hasMoreElements()) {
            String nameParameter = parameterNamesFromRequest.nextElement();
            if (nameParameter.contains(ATTRIBUTE_SUBJECT)) {
                nameParametersContainSubjectFromRequest.add(nameParameter);
            }
        }
        return nameParametersContainSubjectFromRequest;
    }

    static List<SubjectResult> parseSubjectResults(HttpServletRequest request, List<String> nameParametersContainSubjectFromRequest) {
        List<SubjectResult> subjectResults = new ArrayList<>();

        for (String nameParameterContainSubject : nameParametersContainSubjectFromRequest) {
            int subjectId = Integer.parseInt(nameParameterContainSubject.substring(nameParameterContainSubject.lastIndexOf("_") + 1));
            int point = Integer.parseInt(request.getParameter(nameParameterContainSubject));
            SubjectResult subjectResult = new SubjectResult(subjectId, point);
            subjectResults.add(subjectResult);
        }
        return subjectResults;
    }
}
