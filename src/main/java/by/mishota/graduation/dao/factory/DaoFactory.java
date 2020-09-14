package by.mishota.graduation.dao.factory;

import by.mishota.graduation.dao.*;
import by.mishota.graduation.dao.impl.*;
import by.mishota.graduation.dao.pool.ConnectionPool;

public class DaoFactory {

    private static DaoFactory instance = new DaoFactory();

    private UserDao userDao;
    private StudentDao studentDao;
    private SubjectDao subjectDao;
    private FacultyDao facultyDao;
    private SubjectResultDao subjectResultDao;
    private EntrantDao entrantDao;
    private FacultyPriorityDao facultyPriorityDao;
    private PhotoDao photoDao;
    private NewsDao newsDao;

    private DaoFactory() {
        userDao = new UserDaoImpl();
        studentDao = new StudentDaoImpl();
        subjectDao = new SubjectDaoImpl();
        facultyDao = new FacultyDaoImpl();
        subjectResultDao = new SubjectResultDaoImpl();
        entrantDao = new EntrantDaoImpl();
        facultyPriorityDao = new FacultyPriorityDaoImpl();
        photoDao = new PhotoDaoImpl();
        newsDao = new NewsDaoImpl();
    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public SubjectDao getSubjectDao() {
        return subjectDao;
    }

    public FacultyDao getFacultyDao() {
        return facultyDao;
    }

    public SubjectResultDao getSubjectResultDao() {
        return subjectResultDao;
    }

    public EntrantDao getEntrantDao() {
        return entrantDao;
    }

    public FacultyPriorityDao getFacultyPriorityDao() {
        return facultyPriorityDao;
    }

    public PhotoDao getPhotoDao() {
        return photoDao;
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public static void setPathDatabase(String pathDatabase) {
        ConnectionPool.setPathDatabaseProperties(pathDatabase);
    }
}
