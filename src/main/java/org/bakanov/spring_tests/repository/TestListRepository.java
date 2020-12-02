package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.TestList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestListRepository extends JpaRepository<TestList, Integer> {
    List<TestList> findBySubjectId(Integer subjectId);
    List<TestList> findByTeacherId(Integer teacherId);
}
