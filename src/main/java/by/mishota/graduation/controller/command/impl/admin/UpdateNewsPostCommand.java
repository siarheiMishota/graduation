package by.mishota.graduation.controller.command.impl.admin;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.News;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.PagePathManager;
import by.mishota.graduation.service.NewsService;
import by.mishota.graduation.validation.UtilValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;
import static by.mishota.graduation.resource.FilePath.PATH_PAGE_ADMIN_UPDATE_NEWS;

public class UpdateNewsPostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private NewsService newsService;

    public UpdateNewsPostCommand(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_ADMIN_UPDATE_ALL_NEWS_GET);
        router.setRedirect();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

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

        String stringId = request.getParameter(ATTRIBUTE_NEWS_ID);
        String nameEn = request.getParameter(ATTRIBUTE_NEWS_NAME_EN);
        String nameRu = request.getParameter(ATTRIBUTE_NEWS_NAME_RU);
        String briefDescriptionEn = request.getParameter(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_EN);
        String briefDescriptionRu = request.getParameter(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_RU);
        String englishVariable = request.getParameter(ATTRIBUTE_NEWS_ENGLISH_VARIABLE);
        String russianVariable = request.getParameter(ATTRIBUTE_NEWS_RUSSIAN_VARIABLE);

        if (!UtilValidator.positiveNumberValidate(stringId)) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NON_EXISTENT_NEWS);
            router.setPage(CONTROLLER_ADMIN_UPDATE_ALL_NEWS_GET);
            router.setRedirect();
            return router;
        }
        int newsId = Integer.parseInt(stringId);
        try {

            Optional<News> optionalNews = newsService.findById(newsId);
            if (optionalNews.isEmpty()) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NON_EXISTENT_NEWS);
                router.setPage(CONTROLLER_ADMIN_UPDATE_ALL_NEWS_GET);
                router.setRedirect();
                return router;
            }
            News newsByIdFromDb = optionalNews.get();
            Map<String, String> errorsMap = newsService.validateNews(nameRu, nameEn, briefDescriptionRu, briefDescriptionEn,
                    englishVariable, russianVariable);
            if (!errorsMap.isEmpty()) {
                News newsForReturningByErrors = new News(newsId, newsByIdFromDb.getUserCreatorId(), nameRu, nameEn, briefDescriptionRu,
                        briefDescriptionEn, englishVariable, russianVariable, newsByIdFromDb.getCreationDate());
                request.setAttribute(ATTRIBUTE_NEWS, newsForReturningByErrors);
                request.setAttribute(ATTRIBUTE_ERRORS, errorsMap);
                router.setPage(PagePathManager.getProperty(PATH_PAGE_ADMIN_UPDATE_NEWS));
                router.setForward();
                return router;
            }

            News news = new News();
            news.setId(newsId);
            news.setNameRu(nameRu);
            news.setNameEn(nameEn);
            news.setBriefDescriptionRu(briefDescriptionRu);
            news.setBriefDescriptionEn(briefDescriptionEn);
            news.setCreationDate(newsByIdFromDb.getCreationDate());
            news.setEnglishVariable(englishVariable);
            news.setRussianVariable(russianVariable);
            news.setUserCreatorId(newsByIdFromDb.getUserCreatorId());

            if (newsService.update(news) == 1) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_SUCCESS);
            } else {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_SUCCESS);
            }
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check news is error", e);
        } catch (DuplicateException e) {
            logger.warn("Updating news is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        return router;
    }
}
