package org.bakanov.spring_tests.repository;

import org.bakanov.spring_tests.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
//    TODO Fix AnswerRepository
    List<Answer> findByQuestionRow_id(Integer questionId);
}
