package by.mishota.graduation.dao;

import by.mishota.graduation.entity.SubjectResult;

import java.util.List;
import java.util.Optional;

public interface SubjectResultDao {

    Optional<SubjectResult> findById(int id);

    List<SubjectResult> findAllByStudentId(int studentId);
}
