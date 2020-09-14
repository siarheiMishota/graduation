package by.mishota.graduation.service;

import by.mishota.graduation.entity.Entrant;
import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.entity.FacultyPriority;
import by.mishota.graduation.entity.SubjectResult;
import by.mishota.graduation.exception.DuplicateException;
import by.mishota.graduation.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface EntrantService {

    Optional<Entrant> findById(int id) throws ServiceException;

    Optional<Entrant> findByUserId(int userId) throws ServiceException;

    List<Entrant> findAll() throws ServiceException;

    List<Entrant> findFewSkippingFew(int numberFind, int numberSkipping) throws ServiceException;

    Optional<Entrant> add(Entrant entrant) throws ServiceException, DuplicateException;

    Optional<Entrant> addResult(Entrant entrant) throws ServiceException, DuplicateException;

    Optional<Entrant> addPriority(Entrant entrant) throws ServiceException, DuplicateException;

    boolean deletePriority(Entrant entrant, FacultyPriority facultyPriority) throws ServiceException, DuplicateException;

    boolean deletePriorities(Entrant entrant, List<FacultyPriority> facultyPriorities) throws ServiceException, DuplicateException;

    boolean deleteResult(Entrant entrant, SubjectResult subjectResult) throws ServiceException, DuplicateException;

    boolean deleteResults(Entrant entrant, List<SubjectResult> subjectResults) throws ServiceException, DuplicateException;

    int update(Entrant entrant) throws ServiceException, DuplicateException;

    int numberEntrants() throws ServiceException;

    default int sumCertificates(Entrant entrant){
        int sumSubjects = entrant.getResults()
                .stream()
                .mapToInt(SubjectResult::getPoints)
                .sum();
        return entrant.getCertificate() + sumSubjects;
    }

}
