package by.mishota.graduation.command.impl;

import by.mishota.graduation.command.ActionCommand;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.resource.ConfigurationManager;
import by.mishota.graduation.service.FacultyService;
import by.mishota.graduation.service.UserService;
import by.mishota.graduation.service.impl.FacultyServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.mishota.graduation.resource.ParamStringManager.PATH_PAGE_LOGIN;
import static by.mishota.graduation.resource.ParamStringManager.PATH_PAGE_MAIN;

public class MainCommand implements ActionCommand {

    private static Logger logger = LogManager.getLogger();
    private static final String INTERNAL_SERVER_ERROR = "InternalServerError";
    private static final String SERVER_IS_TEMPORARILY_UNAVAILABLE = "Server is temporarily unavailable";

    private UserService userService;

    public MainCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty(PATH_PAGE_LOGIN);
        FacultyService facultyService = new FacultyServiceImpl();
        List<Faculty> faculties;
        try {
            faculties = facultyService.getAll();
            request.setAttribute("faculties", faculties);
            page = ConfigurationManager.getProperty(PATH_PAGE_MAIN);
        } catch (ServiceException e) {
            request.setAttribute(INTERNAL_SERVER_ERROR, SERVER_IS_TEMPORARILY_UNAVAILABLE);
            logger.warn("getting faculties is error", e);
        }

        return page;
    }
}
