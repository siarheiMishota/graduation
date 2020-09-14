package by.mishota.graduation.service;

import by.mishota.graduation.entity.Subject;
import by.mishota.graduation.exception.DaoException;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SubjectService {

    Map<String,String> checkMark(String markString);

    Optional<Subject> findById(int id) throws ServiceException;

    void add(Subject subject) throws ServiceException, DuplicateException;

    int update(Subject subject) throws ServiceException, DuplicateException;

    List<Subject> findAll() throws ServiceException;

    List<Subject> findAllByFacultyId(int facultyId) throws ServiceException;

    List<Integer> findAllIdByFacultyId(int facultyId) throws ServiceException;
}
