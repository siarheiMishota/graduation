package by.mishota.graduation.controller.command.impl.admin;

import by.mishota.graduation.controller.Router;
import by.mishota.graduation.controller.command.ActionCommand;
import by.mishota.graduation.entity.News;
import by.mishota.graduation.entity.Role;
import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

public class AddNewsPostCommand implements ActionCommand {
    private static final Logger logger = LogManager.getLogger();

    private NewsService newsService;

    public AddNewsPostCommand(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(CONTROLLER_ADMIN_ADD_NEWS_GET);
        router.setRedirect();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_ADMIN_ADD_NEWS_GET);
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

        String nameEn = request.getParameter(ATTRIBUTE_NEWS_NAME_EN);
        String nameRu = request.getParameter(ATTRIBUTE_NEWS_NAME_RU);
        String briefDescriptionEn = request.getParameter(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_EN);
        String briefDescriptionRu = request.getParameter(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_RU);
        String englishVariable = request.getParameter(ATTRIBUTE_NEWS_ENGLISH_VARIABLE);
        String russianVariable = request.getParameter(ATTRIBUTE_NEWS_RUSSIAN_VARIABLE);

        Map<String, String> errorsMap = newsService.validateNews(nameRu, nameEn, briefDescriptionRu, briefDescriptionEn,
                englishVariable, russianVariable);
        if (!errorsMap.isEmpty()) {
            settingAttributesInRequestForDefault(request,nameEn,nameRu,briefDescriptionEn,briefDescriptionRu,englishVariable,russianVariable);
            request.setAttribute(ATTRIBUTE_ERRORS, errorsMap);
            router.setForward();
            return router;
        }

        try {
            News news = new News();
            news.setNameRu(nameRu);
            news.setNameEn(nameEn);
            news.setBriefDescriptionRu(briefDescriptionRu);
            news.setBriefDescriptionEn(briefDescriptionEn);
            news.setCreationDate(LocalDateTime.now());
            news.setEnglishVariable(englishVariable);
            news.setRussianVariable(russianVariable);
            news.setUserCreatorId(user.getId());

            if (newsService.add(news)) {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_SUCCESS);
            } else {
                session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_NOT_SUCCESS);
            }
            return router;
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("Check news is error", e);
        } catch (DuplicateException e) {
            logger.warn("Adding news is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            router.setPage(CONTROLLER_MAIN_GET);
            router.setRedirect();
        }

        router.setPage(CONTROLLER_MAIN_GET);
        return router;
    }

    private void settingAttributesInRequestForDefault(HttpServletRequest request, String nameEn, String nameRu,
                                                      String briefDescriptionEn, String briefDescriptionRu,
                                                      String englishVariable, String russianVariable) {
        request.setAttribute(ATTRIBUTE_NEWS_NAME_EN, nameEn);
        request.setAttribute(ATTRIBUTE_NEWS_NAME_RU, nameRu);
        request.setAttribute(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_EN, briefDescriptionEn);
        request.setAttribute(ATTRIBUTE_NEWS_BRIEF_DESCRIPTION_RU, briefDescriptionRu);
        request.setAttribute(ATTRIBUTE_NEWS_ENGLISH_VARIABLE, englishVariable);
        request.setAttribute(ATTRIBUTE_NEWS_RUSSIAN_VARIABLE, russianVariable);
    }
}
