package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.StudentAnswer;
import org.bakanov.spring_tests.model.TestList;
import org.bakanov.spring_tests.model.User;
import org.bakanov.spring_tests.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentAnswerController {
    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestListRepository testListRepository;

    @GetMapping("/users/{userId}/testLists/{testListId}/answers/{answerId}/studentAnswers")
    public List<StudentAnswer> getStudentAnswers(@PathVariable Integer userId,
                                                 @PathVariable Integer testListId,
                                                 @PathVariable Integer answerId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return studentAnswerRepository.findByAnswerId(answerId);
    }

    @PostMapping("/users/{userId}/testLists/{testListId}/answers/{answerId}/studentAnswers")
    public StudentAnswer addStudentAnswer(@PathVariable Integer userId,
                                          @PathVariable Integer testListId,
                                          @PathVariable Integer answerId,
                                          @Valid @RequestBody StudentAnswer studentAnswer) {
        User user;
        TestList testList;
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        } else { user = userRepository.getOne(userId); }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        } else { testList = testListRepository.getOne(testListId); }
        return answerRepository.findById(answerId)
                .map(answer -> {
                    studentAnswer.setUser(user);
                    studentAnswer.setTestList(testList);
                    studentAnswer.setAnswer(answer);
                    return studentAnswerRepository.save(studentAnswer);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
    }

    @PutMapping("/users/{userId}/testLists/{testListId}/answers/{answerId}/studentAnswers/{studentAnswerId}")
    public StudentAnswer updateStudentAnswer(@PathVariable Integer userId,
                                             @PathVariable Integer testListId,
                                             @PathVariable Integer answerId,
                                             @PathVariable Integer studentAnswerId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        if (!answerRepository.existsById(answerId)) {
            throw new ResourceNotFoundException("Answer not found with id " + answerId);
        }
        return studentAnswerRepository.findById(studentAnswerId)
                .map(studentAnswer -> studentAnswerRepository.save(studentAnswer)).orElseThrow(
                        () -> new ResourceNotFoundException("StudentAnswer not found with id " + studentAnswerId));
    }

    @DeleteMapping("/users/{userId}/testLists/{testListId}/answers/{answerId}/studentAnswers/{studentAnswerId}")
    public ResponseEntity<?> deleteStudentAnswer(@PathVariable Integer userId,
                                                 @PathVariable Integer testListId,
                                                 @PathVariable Integer answerId,
                                                 @PathVariable Integer studentAnswerId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        if (!answerRepository.existsById(answerId)) {
            throw new ResourceNotFoundException("Answer not found with id " + answerId);
        }
        return studentAnswerRepository.findById(studentAnswerId)
                .map(studentAnswer -> {
                    studentAnswerRepository.delete(studentAnswer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("StudentAnswer not found with id " + studentAnswerId));
    }
}