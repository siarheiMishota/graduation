package by.mishota.graduation.service.impl;

import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.impl.FacultyDaoImpl;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.FacultyService;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    @Override
    public List<Faculty> getAll() throws ServiceException {

        List<Faculty> faculties;

        FacultyDao facultyDao = new FacultyDaoImpl();
        try {
            faculties = facultyDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return faculties;

    }
}
