package by.mishota.graduation.controller.command.impl.admin;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.*;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.EntrantService;
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
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ADMIN_VIEW_ENTRANTS;
import static java.lang.Math.ceil;

public class ViewEntrantsGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private final int numberPageDefault = 1;

    private int numberRowsOnPage = 10;
    private SubjectService subjectService;
    private EntrantService entrantService;



    public ViewEntrantsGetCommand(SubjectService subjectService, EntrantService entrantService) {
        this.subjectService = subjectService;
        this.entrantService = entrantService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_ADMIN_VIEW_ENTRANTS));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        String stringNumberPage = request.getParameter(ATTRIBUTE_PAGE);
        int numberOfPage;



        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_ADMIN_VIEW_ENTRANTS_GET);
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

        if (UtilValidator.positiveNumberValidate(stringNumberPage)) {
            numberOfPage = Integer.parseInt(stringNumberPage);
        } else {
            numberOfPage = numberPageDefault;
        }

        try {

            int numberRows = entrantService.numberEntrants();
            int numberOfPages = (int) ceil(1.0 * numberRows / numberRowsOnPage);
            if (numberOfPage > numberOfPages) {
                router.setPage(CONTROLLER_ADMIN_VIEW_ENTRANTS_GET + "&&page=" + numberOfPages);
                router.setRedirect();
                return router;
            }
            int numberSkippedRows = (numberOfPage - 1) * numberRowsOnPage;

            List<Entrant> entrants = entrantService.findFewSkippingFew(numberRowsOnPage, numberSkippedRows);
            Map<Integer, String> idAndNameSubjectsMap = subjectService.findAll()
                    .stream()
                    .collect(Collectors.toMap(Subject::getId, Subject::getName));

            Map<String,String> stringIdAndNameSubjectsMap=new HashMap<>();

            for (Map.Entry<Integer,String> entry:idAndNameSubjectsMap.entrySet()) {
                stringIdAndNameSubjectsMap.put(Integer.toString(entry.getKey()),entry.getValue());
            }

            request.setAttribute(ATTRIBUTE_NUMBER_OF_PAGE, numberOfPage);
            request.setAttribute(ATTRIBUTE_NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(ATTRIBUTE_SUBJECT_NAMES, stringIdAndNameSubjectsMap);
            request.setAttribute(ATTRIBUTE_ENTRANTS, entrants);
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
