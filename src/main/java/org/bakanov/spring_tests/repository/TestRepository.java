package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Integer> {
    List<Test> findByTestListId(Integer testListId);
    List<Test> findByQuestionId(Integer questionId);
}
