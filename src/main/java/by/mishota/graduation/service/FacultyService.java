package by.mishota.graduation.service;

import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface FacultyService {

    List<Faculty> findAll() throws ServiceException;

    Optional<Faculty> findById(int id) throws ServiceException;

    List<Faculty> findAllByNeedSubjects(List<Integer> idSubjects) throws ServiceException;

    List<Faculty> findFewSkippingFew(int numberFind, int numberSkipping) throws ServiceException;

    int numberFaculties() throws ServiceException;

    boolean isExistId(int id) throws ServiceException;

    boolean validateFacultyId(String facultyIdString);

    boolean validateFacultiesId(List<String> facultiesIdString);
}
