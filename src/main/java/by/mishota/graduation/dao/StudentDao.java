package by.mishota.graduation.dao;

import by.mishota.graduation.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao extends Dao{

    List<Student> findAll();
    List<Student> findByFaculty();
    List<Student> findByFree();
    List<Student> findByPay();
    List<Student> findAllFemale();
    List<Student> findAllMale();
    Optional<Student> findById();



}
