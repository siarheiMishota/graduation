package by.mishota.graduation.controller.command.impl.admin;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.News;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.NewsService;
import by.mishota.graduation.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ADMIN_UPDATE_ALL_NEWS;
import static java.lang.Math.ceil;

public class UpdateAllNewsGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final int numberPageDefault = 1;
    private static final int numberRowsOnPage = 10;

    private NewsService newsService;

    public UpdateAllNewsGetCommand(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_ADMIN_UPDATE_ALL_NEWS));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        String stringNumberPage = request.getParameter(ATTRIBUTE_PAGE);
        int numberOfPage;


        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_ADMIN_UPDATE_ALL_NEWS_GET);
            router.setPage(CONTROLLER_LOGIN_GET);
            router.setRedirect();
            return router;
        }

        if (user.getRole() != Role.ADMIN) {
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

            int numberRows = newsService.numberNews();
            int numberOfPages = (int) ceil(1.0 * numberRows / numberRowsOnPage);
            if (numberOfPage > numberOfPages) {
                router.setPage(CONTROLLER_ADMIN_UPDATE_ALL_NEWS_GET);
                router.setRedirect();
                return router;
            }
            int numberSkippedRows = (numberOfPage - 1) * numberRowsOnPage;

            List<News> someNews = newsService.findFewSkippingFew(numberRowsOnPage, numberSkippedRows);

            request.setAttribute(ATTRIBUTE_NUMBER_OF_PAGE, numberOfPage);
            request.setAttribute(ATTRIBUTE_NUMBER_OF_PAGES, numberOfPages);
            request.setAttribute(ATTRIBUTE_ALL_NEWS, someNews);
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
