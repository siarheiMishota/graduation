package by.mishota.graduation.service.impl;

import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.FacultyService;
import by.mishota.graduation.validation.FacultyValidator;

import java.util.List;
import java.util.Optional;

public class FacultyServiceImpl implements FacultyService {

    @Override
    public List<Faculty> findAll() throws ServiceException {

        List<Faculty> faculties;

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            faculties = facultyDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return faculties;
    }

    @Override
    public Optional<Faculty> findById(int id) throws ServiceException {
        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            return facultyDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Faculty> findAllByNeedSubjects(List<Integer> idSubjects) throws ServiceException {
        try {
            return DaoFactory.getInstance().getFacultyDao().findAllByNeedSubjects(idSubjects);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Faculty> findFewSkippingFew(int numberFind, int numberSkipping) throws ServiceException {
        List<Faculty> faculties;

        FacultyDao facultyDao = DaoFactory.getInstance().getFacultyDao();
        try {
            faculties = facultyDao.findFewSkippingFew(numberFind, numberSkipping);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return faculties;
    }

    @Override
    public int numberFaculties() throws ServiceException {
        try {
            return DaoFactory.getInstance().getFacultyDao().numberFaculties();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean isExistId(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getFacultyDao().isExistId(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean validateFacultyId(String facultyIdString) {
        return FacultyValidator.validateFacultyId(facultyIdString);
    }

    @Override
    public boolean validateFacultiesId(List<String> facultiesIdString) {
        return FacultyValidator.validateFacultiesId(facultiesIdString);
    }
}
