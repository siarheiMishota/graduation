package by.mishota.graduation.controller.command.impl.admin;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.News;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.NewsService;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ADMIN_VIEW_NEWS;

public class ViewNewsGetCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private NewsService newsService;
    private UserService userService;

    public ViewNewsGetCommand(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePathManager.getProperty(PATH_PAGE_ADMIN_VIEW_NEWS));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        String stringNewsId = request.getParameter(ATTRIBUTE_NEWS_ID);
        int newsId;


        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_ADMIN_VIEW_NEWS_GET + "newsId=" + stringNewsId);
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

        if (UtilValidator.positiveNumberValidate(stringNewsId)) {
            newsId = Integer.parseInt(stringNewsId);
        } else {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NON_EXISTENT_NEWS);
            router.setPage(CONTROLLER_ADMIN_VIEW_ALL_NEWS_GET);
            router.setRedirect();
            return router;
        }

        try {

            Optional<News> optionalNews = newsService.findById(newsId);

            if (optionalNews.isEmpty()) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NON_EXISTENT_NEWS);
                router.setPage(CONTROLLER_ADMIN_VIEW_ALL_NEWS_GET);
                router.setRedirect();
                return router;
            }

            News news = optionalNews.get();
            Optional<User> optionalUser = userService.findById(news.getUserCreatorId());
            optionalUser.ifPresent(non->request.setAttribute(ATTRIBUTE_USER,optionalUser.get()));
            request.setAttribute(ATTRIBUTE_NEWS, news);
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
