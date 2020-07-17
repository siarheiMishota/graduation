package by.mishota.graduation.dao.factory;

import by.mishota.graduation.dao.*;
import by.mishota.graduation.dao.impl.*;
import by.mishota.graduation.dao.pool.ConnectionPool;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.exception.ConnectionPoolException;

import java.util.concurrent.atomic.AtomicBoolean;

public class DaoFactory {

    private static DaoFactory daoFactory=new DaoFactory();

    private UserDao userDao;
    private StudentDao studentDao;
    private SubjectDao subjectDao;
    private FacultyDao facultyDao;
    private SubjectResultDao subjectResultDao;
    private EntrantDao entrantDao;

    private DaoFactory() {
        userDao=new UserDaoImpl();
        studentDao=new StudentDaoImpl();
        subjectDao=new SubjectDaoImpl();
        facultyDao=new FacultyDaoImpl();
        subjectResultDao=new SubjectResultDaoImpl();
        entrantDao=new EntrantDaoImpl();
    }

    public static DaoFactory getInstance(){
        return daoFactory;
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
}
