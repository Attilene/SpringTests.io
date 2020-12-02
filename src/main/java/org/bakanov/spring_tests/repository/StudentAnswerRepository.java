package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Integer> {
    List<StudentAnswer> findByTestListId(Integer testListId);
    List<StudentAnswer> findByUserId(Integer userId);
    List<StudentAnswer> findByAnswerId(Integer answerId);
}
