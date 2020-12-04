package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.TestList;
import org.bakanov.spring_tests.model.User;
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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/subject/{subjectId}/testLists")
    public List<TestList> getTestListsBySubjectId(@PathVariable Integer subjectId) {
        return testListRepository.findBySubjectId(subjectId);
    }

    @GetMapping("/roles/{roleId}/users/{userId}/testLists")
    public List<TestList> getTestListsByTeacherIdAndRole(@PathVariable Integer roleId,
                                                         @PathVariable Integer userId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        return testListRepository.findByTeacherId(userId);
    }

    @GetMapping("/groups/{groupId}/users/{userId}/testLists")
    public List<TestList> getTestListsByTeacherIdAndGroup(@PathVariable Integer groupId,
                                                          @PathVariable Integer userId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        return testListRepository.findByTeacherId(userId);
    }

    @PostMapping("/subject/{subjectId}/testLists")
    public TestList addSubjectToTestList(@PathVariable Integer subjectId,
                                         @Valid @RequestBody TestList testList) {
        return subjectRepository.findById(subjectId)
                .map(subject -> {
                    testList.setSubject(subject);
                    return testListRepository.save(testList);
                }).orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
    }

    @PostMapping("/roles/{roleId}/users/{userId}/testLists")
    public TestList addTeacherToTestListByRole(@PathVariable Integer roleId,
                                               @PathVariable Integer userId,
                                               @Valid @RequestBody TestList testList) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        return userRepository.findById(userId)
                .map(user -> {
                    testList.setTeacher(user);
                    return testListRepository.save(testList);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PostMapping("/groups/{groupId}/users/{userId}/testLists")
    public TestList addTeacherToTestListByGroup(@PathVariable Integer groupId,
                                                @PathVariable Integer userId,
                                                @Valid @RequestBody TestList testList) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        return userRepository.findById(userId)
                .map(user -> {
                    testList.setTeacher(user);
                    return testListRepository.save(testList);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PutMapping("/subject/{subjectId}/testLists/{testListId}")
    public TestList updateTestListAndSubject(@PathVariable Integer subjectId,
                                             @PathVariable Integer testListId,
                                             @Valid @RequestBody TestList testListRequest){
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    testList.setName(testListRequest.getName());
                    return testListRepository.save(testList);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PutMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}")
    public TestList updateTestListAndTeacherByRole(@PathVariable Integer roleId,
                                                   @PathVariable Integer userId,
                                                   @PathVariable Integer testListId,
                                                   @Valid @RequestBody TestList testListRequest){
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
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

    @PutMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}")
    public TestList updateTestListAndTeacherByGroup(@PathVariable Integer groupId,
                                                    @PathVariable Integer userId,
                                                    @PathVariable Integer testListId,
                                                    @Valid @RequestBody TestList testListRequest){
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
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

    @DeleteMapping("/subject/{subjectId}/testLists/{testListId}")
    public ResponseEntity<?> deleteTestListBySubject(@PathVariable Integer subjectId,
                                                     @PathVariable Integer testListId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    testListRepository.delete(testList);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @DeleteMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}")
    public ResponseEntity<?> deleteTestListByTeacherAndRole(@PathVariable Integer roleId,
                                                            @PathVariable Integer userId,
                                                            @PathVariable Integer testListId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
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

    @DeleteMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}")
    public ResponseEntity<?> deleteTestListByTeacherAndGroup(@PathVariable Integer groupId,
                                                             @PathVariable Integer userId,
                                                             @PathVariable Integer testListId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
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
