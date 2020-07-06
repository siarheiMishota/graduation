package by.mishota.graduation.service;

import by.mishota.graduation.entity.Faculty;
import by.mishota.graduation.exception.ServiceException;

import java.util.List;

public interface FacultyService {

    List<Faculty> getAll()throws ServiceException;
}
