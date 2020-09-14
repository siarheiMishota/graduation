package by.mishota.graduation.controller.command.impl.admin;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.NewsService;
import by.mishota.graduation.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class DeleteAllNewsPostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private NewsService newsService;

    public DeleteAllNewsPostCommand(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_ADMIN_DELETE_ALL_NEWS_GET);
        router.setRedirect();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_ADMIN_DELETE_ALL_NEWS_GET);
            router.setPage(CONTROLLER_LOGIN_GET);
            return router;
        }

        if (user.getRole() != Role.ADMIN) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_ACCESS);
            router.setPage(CONTROLLER_MAIN_GET);
            return router;
        }
        try {
            String[] stringIds = request.getParameterValues(ATTRIBUTE_NEWS);

            if (stringIds == null) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_SELECTED);
                router.setPage(CONTROLLER_ADMIN_DELETE_ALL_NEWS_GET);
                return router;
            }

            for (String stringId : stringIds) {
                if (!UtilValidator.positiveNumberValidate(stringId)) {
                    session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NON_EXISTENT_NEWS);
                    router.setPage(CONTROLLER_ADMIN_DELETE_ALL_NEWS_GET);
                    return router;
                }
            }

            List<Integer> listId = Arrays.stream(stringIds)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            if (newsService.deleteSome(listId)) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_SUCCESS);
            } else {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_SUCCESS);
            }
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check user is error", e);
        }
        router.setPage(CONTROLLER_MAIN_GET);
        return router;

    }
}
