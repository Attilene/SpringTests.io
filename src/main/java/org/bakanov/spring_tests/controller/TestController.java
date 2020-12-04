package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Test;
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

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/questions/{questionId}/tests")
    public List<Test> getTestsByQuestionId(@PathVariable Integer questionId) {
        return testRepository.findByQuestionId(questionId);
    }

    @GetMapping("/subject/{subjectId}/testLists/{testListId}/tests")
    public List<Test> getTestsByTestListIdAndSubject(@PathVariable Integer subjectId,
                                                     @PathVariable Integer testListId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return testRepository.findByTestListId(testListId);
    }

    @GetMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/tests")
    public List<Test> getTestsByTestListIdAndUserWithRole(@PathVariable Integer roleId,
                                                          @PathVariable Integer userId,
                                                          @PathVariable Integer testListId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testRepository.findByTestListId(testListId);
    }

    @GetMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/tests")
    public List<Test> getTestsByTestListIdAndUserWithGroup(@PathVariable Integer groupId,
                                                           @PathVariable Integer userId,
                                                           @PathVariable Integer testListId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testRepository.findByTestListId(testListId);
    }

    @PostMapping("/questions/{questionId}/tests")
    public Test addTestByQuestion(@PathVariable Integer questionId,
                                  @Valid @RequestBody Test test) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    test.setQuestion(question);
                    return testRepository.save(test);
                }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @PostMapping("/subject/{subjectId}/testLists/{testListId}/tests")
    public Test addTestListBySubjectToTest(@PathVariable Integer subjectId,
                                           @PathVariable Integer testListId,
                                           @Valid @RequestBody Test test) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    test.setTestList(testList);
                    return testRepository.save(test);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PostMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/tests")
    public Test addTestListByUserAndRoleToTest(@PathVariable Integer roleId,
                                               @PathVariable Integer userId,
                                               @PathVariable Integer testListId,
                                               @Valid @RequestBody Test test) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    test.setTestList(testList);
                    return testRepository.save(test);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PostMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/tests")
    public Test addTestListByUserAndGroupToTest(@PathVariable Integer groupId,
                                                @PathVariable Integer userId,
                                                @PathVariable Integer testListId,
                                                @Valid @RequestBody Test test) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    test.setTestList(testList);
                    return testRepository.save(test);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @DeleteMapping("/questions/{questionId}/tests/{testId}")
    public ResponseEntity<?> deleteTestByQuestion(@PathVariable Integer questionId,
                                                  @PathVariable Integer testId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }
        return testRepository.findById(testId)
                .map(test -> {
                    testRepository.delete(test);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Test not found with id " + testId));
    }

    @DeleteMapping("/subject/{subjectId}/testLists/{testListId}/tests/{testId}")
    public ResponseEntity<?> deleteTestByTestListAndSubject(@PathVariable Integer subjectId,
                                                            @PathVariable Integer testListId,
                                                            @PathVariable Integer testId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return testRepository.findById(testId)
                .map(test -> {
                    testRepository.delete(test);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Test not found with id " + testId));
    }

    @DeleteMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/tests/{testId}")
    public ResponseEntity<?> deleteTestByTestListWithUserAndRole(@PathVariable Integer roleId,
                                                                 @PathVariable Integer userId,
                                                                 @PathVariable Integer testListId,
                                                                 @PathVariable Integer testId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return testRepository.findById(testId)
                .map(test -> {
                    testRepository.delete(test);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Test not found with id " + testId));
    }

    @DeleteMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/tests/{testId}")
    public ResponseEntity<?> deleteTestByTestListWithUserAndGroup(@PathVariable Integer groupId,
                                                                  @PathVariable Integer userId,
                                                                  @PathVariable Integer testListId,
                                                                  @PathVariable Integer testId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return testRepository.findById(testId)
                .map(test -> {
                    testRepository.delete(test);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Test not found with id " + testId));
    }
}
