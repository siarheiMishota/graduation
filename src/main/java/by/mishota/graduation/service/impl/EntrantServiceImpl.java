package by.mishota.graduation.service.impl;

import by.mishota.graduation.dao.EntrantDao;
import by.mishota.graduation.dao.FacultyDao;
import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.EntrantService;

import java.util.List;
import java.util.Optional;

public class EntrantServiceImpl implements EntrantService {

    @Override
    public Optional<Entrant> add(Entrant entrant) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getEntrantDao().add(entrant);
        } catch (DaoException e) {
            throw new ServiceException("Error adding entrant", e);
        }
    }

    @Override
    public Optional<Entrant> addResult(Entrant entrant) throws ServiceException, DuplicateException {

        try {
            return DaoFactory.getInstance().getEntrantDao().addResult(entrant);
        } catch (DaoException e) {
            throw new ServiceException("Error adding new result", e);
        }
    }

    @Override
    public Optional<Entrant> addPriority(Entrant entrant) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getEntrantDao().addPriorities(entrant);
        } catch (DaoException e) {
            throw new ServiceException("Error adding new priority", e);
        }
    }

    @Override
    public boolean deletePriority(Entrant entrant, FacultyPriority facultyPriority) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getEntrantDao().deletePriority(entrant, facultyPriority);
        } catch (DaoException e) {
            throw new ServiceException("Error deleting priority of faculties for entrant", e);
        }
    }

    @Override
    public boolean deletePriorities(Entrant entrant, List<FacultyPriority> facultyPriorities) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getEntrantDao().deletePriorities(entrant, facultyPriorities);
        } catch (DaoException e) {
            throw new ServiceException("Error deleting priorities of faculties for entrant", e);
        }
    }

    @Override
    public boolean deleteResult(Entrant entrant, SubjectResult subjectResult) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getEntrantDao().deleteResult(entrant, subjectResult);
        } catch (DaoException e) {
            throw new ServiceException("Error deleting subject of result for entrant", e);
        }
    }

    @Override
    public boolean deleteResults(Entrant entrant, List<SubjectResult> subjectResults) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getEntrantDao().deleteResults(entrant, subjectResults);
        } catch (DaoException e) {
            throw new ServiceException("Error deleting subject of results for entrant", e);
        }
    }

    @Override
    public Optional<Entrant> findById(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getEntrantDao().findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error finding entrant by id", e);
        }
    }

    @Override
    public Optional<Entrant> findByUserId(int userId) throws ServiceException {
        try {
            return DaoFactory.getInstance().getEntrantDao().findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Error finding entrant by user id", e);
        }
    }

    @Override
    public List<Entrant> findAll() throws ServiceException {
        try {
            return DaoFactory.getInstance().getEntrantDao().findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error finding all entrants", e);
        }
    }

    @Override
    public List<Entrant> findFewSkippingFew(int numberFind, int numberSkipping) throws ServiceException {
        List<Entrant> entrants;
        EntrantDao entrantDao = DaoFactory.getInstance().getEntrantDao();
        try {
            entrants = entrantDao.findFewSkippingFew(numberFind, numberSkipping);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return entrants;
    }

    @Override
    public int update(Entrant entrant) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getEntrantDao().update(entrant);
        } catch (DaoException e) {
            throw new ServiceException("Error updating entrant", e);
        }
    }

    @Override
    public int numberEntrants() throws ServiceException {
        try {
            return DaoFactory.getInstance().getEntrantDao().numberEntrants();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
