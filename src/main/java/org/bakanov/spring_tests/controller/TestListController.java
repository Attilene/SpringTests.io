package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Subject;
import org.bakanov.spring_tests.model.TestList;
import org.bakanov.spring_tests.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TestListController {
    @Autowired
    private TestListRepository testListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subject/{subjectId}/users/{userId}/testLists")
    public List<TestList> getTestLists(@PathVariable Integer subjectId,
                                       @PathVariable Integer userId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return testListRepository.findByTeacherId(userId);
    }

    @PostMapping("/subject/{subjectId}/users/{userId}/testLists")
    public TestList addTestList(@PathVariable Integer subjectId,
                                @PathVariable Integer userId,
                                @Valid @RequestBody TestList testList) {
        Subject subject;
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        } else { subject = subjectRepository.getOne(subjectId); }
        return userRepository.findById(userId)
                .map(user -> {
                    testList.setSubject(subject);
                    testList.setTeacher(user);
                    return testListRepository.save(testList);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PutMapping("/subject/{subjectId}/users/{userId}/testLists/{testListId}")
    public TestList updateTestList(@PathVariable Integer subjectId,
                                   @PathVariable Integer userId,
                                   @PathVariable Integer testListId,
                                   @Valid @RequestBody TestList testListRequest){
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    testList.setName(testListRequest.getName());
                    return testListRepository.save(testList);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @DeleteMapping("/subject/{subjectId}/users/{userId}/testLists/{testListId}")
    public ResponseEntity<?> deleteTestList(@PathVariable Integer subjectId,
                                            @PathVariable Integer userId,
                                            @PathVariable Integer testListId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    testListRepository.delete(testList);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }
}
