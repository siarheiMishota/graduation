package by.mishota.graduation.service;

import by.mishota.graduation.service.impl.*;

public class ServiceFactory {

    private static ServiceFactory instance = new ServiceFactory();

    private UserService userService;
    private SubjectService subjectService;
    private FacultyService facultyService;
    private EntrantService entrantService;
    private NewsService newsService;

    private ServiceFactory() {
        this.userService = new UserServiceImpl();
        this.subjectService = new SubjectServiceImpl();
        this.facultyService = new FacultyServiceImpl();
        this.entrantService = new EntrantServiceImpl();
        this.newsService=new NewsServiceImpl();
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public FacultyService getFacultyService() {
        return facultyService;
    }

    public EntrantService getEntrantService() {
        return entrantService;
    }

    public NewsService getNewsService() {
        return newsService;
    }
}
