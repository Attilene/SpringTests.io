package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Test;
import org.bakanov.spring_tests.model.TestList;
import org.bakanov.spring_tests.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestListRepository testListRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/testLists/{testListId}/questions/{questionId}/tests")
    public List<Test> getTests(@PathVariable Integer testListId,
                               @PathVariable Integer questionId) {
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return testRepository.findByQuestionId(questionId);
    }

    @PostMapping("/testLists/{testListId}/questions/{questionId}/tests")
    public Test addTest(@PathVariable Integer testListId,
                        @PathVariable Integer questionId,
                        @Valid @RequestBody Test test) {
        TestList testList;
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        } else { testList = testListRepository.getOne(testListId); }
        return questionRepository.findById(questionId)
                .map(question -> {
                    test.setTestList(testList);
                    test.setQuestion(question);
                    return testRepository.save(test);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @PutMapping("/testLists/{testListId}/questions/{questionId}/tests/{testId}")
    public Test updateTest(@PathVariable Integer testListId,
                           @PathVariable Integer questionId,
                           @PathVariable Integer testId) {
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }
        return testRepository.findById(testId)
                .map(test -> testRepository.save(test)).orElseThrow(
                        () -> new ResourceNotFoundException("Test not found with id " + testId));
    }

    @DeleteMapping("/testLists/{testListId}/questions/{questionId}/tests/{testId}")
    public ResponseEntity<?> deleteTest(@PathVariable Integer testListId,
                                        @PathVariable Integer questionId,
                                        @PathVariable Integer testId) {
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }
        return testRepository.findById(testId)
                .map(test -> {
                    testRepository.delete(test);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Test not found with id " + testId));
    }
}
