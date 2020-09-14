package by.mishota.graduation.service.impl;

import by.mishota.graduation.dao.factory.DaoFactory;
import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;
import by.mishota.graduation.service.SubjectService;
import by.mishota.graduation.validation.EnterCertificatesValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SubjectServiceImpl implements SubjectService {

    @Override
    public Map<String, String> checkMark(String markString) {
        return EnterCertificatesValidator.validateMark(markString);
    }
    
    

    @Override
    public List<Subject> findAll() throws ServiceException {
        try {
            return DaoFactory.getInstance().getSubjectDao().findAll();
        } catch (DaoException e) {
            throw new ServiceException("Error getting  all subjects", e);
        }
    }

    @Override
    public List<Integer> findAllIdByFacultyId(int facultyId) throws ServiceException {
        try {
            return DaoFactory.getInstance().getSubjectDao().findAllIdByFacultyId(facultyId);
        } catch (DaoException e) {
            throw new ServiceException("Error getting  id of subjects for faculty id", e);
        }
    }

    @Override
    public List<Subject> findAllByFacultyId(int facultyId) throws ServiceException {
        try {
            return DaoFactory.getInstance().getSubjectDao().findAllByFacultyId(facultyId);
        } catch (DaoException e) {
            throw new ServiceException("Error getting subjects for faculty id", e);
        }
    }

    @Override
    public int update(Subject subject) throws ServiceException, DuplicateException {
        try {
            return DaoFactory.getInstance().getSubjectDao().update(subject);
        } catch (DaoException e) {
            throw new ServiceException("Error updating subject", e);
        }
    }

    @Override
    public Optional<Subject> findById(int id) throws ServiceException {
        try {
            return DaoFactory.getInstance().getSubjectDao().findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Error getting  subject for id", e);
        }
    }

    @Override
    public void add(Subject subject) throws ServiceException, DuplicateException {
        try {
            DaoFactory.getInstance().getSubjectDao().add(subject);
        } catch (DaoException e) {
            throw new ServiceException("Error adding subject ", e);
        }
    }
}
