package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyDao extends Dao{

    List<Faculty> findAll();

    Optional<Faculty> findById();

    List<Faculty> findWhereFreeMoreSpecify(int specifyPlaces);


}
