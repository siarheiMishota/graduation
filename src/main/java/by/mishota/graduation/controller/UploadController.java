package by.mishota.graduation.controller;

import by.mishota.graduation.entity.User;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static by.mishota.graduation.controller.Attribute.*;
import static by.mishota.graduation.controller.command.ParamStringCommand.*;

@WebServlet(urlPatterns = "/upload/add_photo",
        initParams = {@WebInitParam(name = "uploadPath", value = "D:\\images/")})
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            session.setAttribute(ATTRIBUTE_REFERER, CONTROLLER_UPLOAD_PHOTO_GET);
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_USER_NULL);
            response.sendRedirect(CONTROLLER_LOGIN_GET);
            return;
        }

        if (user.getPhotos().size() >= 12) {
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PHOTOS_FILL_IN);
            response.sendRedirect(CONTROLLER_MAIN_GET);
        }

        String uploadFileDir = getInitParameter("uploadPath");
        File fileSaveDir = new File(uploadFileDir);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        Part part = request.getPart("image");
        String name = part.getSubmittedFileName();

        Map<String, String> errorsExtension = userService.checkExtensionPhoto(name);
        if (!errorsExtension.isEmpty()) {
            session.setAttribute(ATTRIBUTE_ERRORS, errorsExtension);
            response.sendRedirect(CONTROLLER_UPLOAD_PHOTO_GET);
            return;

        }
        try {
            String newNameFile = UUID.randomUUID().toString() +
                    part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf("."));
            part.write(uploadFileDir + newNameFile);
            userService.addPhoto(user, newNameFile);
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PHOTO_UPLOAD_SUCCESSFULLY);
        } catch (IOException | ServiceException e) {
            logger.info("Photo isn't uploaded", e);
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            session.setAttribute(ATTRIBUTE_MESSAGE, VALUE_ATTRIBUTE_PHOTO_UPLOAD_FAILED);
        } catch (DuplicateException e) {
            logger.warn("Updating profile is error", e);
            session.setAttribute(ATTRIBUTE_MESSAGE,VALUE_ATTRIBUTE_DUPLICATE);

            response.sendRedirect(CONTROLLER_MAIN_GET);
            return;
        }

        String page;
        if (user.getPhotos().size() == 12) {
            page = CONTROLLER_VIEW_PHOTOS_GET;
        } else {
            page = CONTROLLER_UPLOAD_PHOTO_GET;
        }
        response.sendRedirect(page);
    }
}
