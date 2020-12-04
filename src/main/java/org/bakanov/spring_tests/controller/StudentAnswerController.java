package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.StudentAnswer;
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
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TestListRepository testListRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/questions/{questionId}/answers/{answerId}/studentAnswers")
    public List<StudentAnswer> getStudentAnswersByQuestionAndAnswer(@PathVariable Integer questionId,
                                                                    @PathVariable Integer answerId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }
        return studentAnswerRepository.findByAnswerId(answerId);
    }

    @GetMapping("/roles/{roleId}/users/{userId}/studentAnswers")
    public List<StudentAnswer> getStudentAnswersByRoleAndUser(@PathVariable Integer roleId,
                                                              @PathVariable Integer userId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        return studentAnswerRepository.findByUserId(userId);
    }

    @GetMapping("/groups/{groupId}/users/{userId}/studentAnswers")
    public List<StudentAnswer> getStudentAnswersByGroupAndUser(@PathVariable Integer groupId,
                                                               @PathVariable Integer userId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        return studentAnswerRepository.findByUserId(userId);
    }

    @GetMapping("/subject/{subjectId}/testLists/{testListId}/studentAnswers")
    public List<StudentAnswer> getStudentAnswersByTestListAndSubject(@PathVariable Integer subjectId,
                                                                     @PathVariable Integer testListId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return studentAnswerRepository.findByTestListId(testListId);
    }

    @GetMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/studentAnswers")
    public List<StudentAnswer> getStudentAnswersByTestListIdAndUserWithRole(@PathVariable Integer roleId,
                                                                            @PathVariable Integer userId,
                                                                            @PathVariable Integer testListId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return studentAnswerRepository.findByTestListId(testListId);
    }

    @GetMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/studentAnswers")
    public List<StudentAnswer> getStudentAnswersByTestListIdAndUserWithGroup(@PathVariable Integer groupId,
                                                                             @PathVariable Integer userId,
                                                                             @PathVariable Integer testListId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return studentAnswerRepository.findByTestListId(testListId);
    }

    @PostMapping("/questions/{questionId}/answers/{answerId}/studentAnswers")
    public StudentAnswer addStudentAnswerByQuestionAndAnswer(@PathVariable Integer questionId,
                                                              @PathVariable Integer answerId,
                                                              @Valid @RequestBody StudentAnswer studentAnswer) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }
        return answerRepository.findById(answerId)
                .map(answer -> {
                    studentAnswer.setAnswer(answer);
                    return studentAnswerRepository.save(studentAnswer);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
    }

    @PostMapping("/roles/{roleId}/users/{userId}/studentAnswers")
    public StudentAnswer addStudentAnswerByRoleAndUser(@PathVariable Integer roleId,
                                                       @PathVariable Integer userId,
                                                       @Valid @RequestBody StudentAnswer studentAnswer) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        return userRepository.findById(userId)
                .map(user -> {
                    studentAnswer.setUser(user);
                    return studentAnswerRepository.save(studentAnswer);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PostMapping("/groups/{groupId}/users/{userId}/studentAnswers")
    public StudentAnswer addStudentAnswerByGroupAndUser(@PathVariable Integer groupId,
                                                        @PathVariable Integer userId,
                                                        @Valid @RequestBody StudentAnswer studentAnswer) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        return userRepository.findById(userId)
                .map(user -> {
                    studentAnswer.setUser(user);
                    return studentAnswerRepository.save(studentAnswer);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @PostMapping("/subject/{subjectId}/testLists/{testListId}/studentAnswers")
    public StudentAnswer addStudentAnswerBySubjectToSchedule(@PathVariable Integer subjectId,
                                                             @PathVariable Integer testListId,
                                                             @Valid @RequestBody StudentAnswer studentAnswer) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    studentAnswer.setTestList(testList);
                    return studentAnswerRepository.save(studentAnswer);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PostMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/studentAnswers")
    public StudentAnswer addStudentAnswerByUserAndRoleToSchedule(@PathVariable Integer roleId,
                                                                 @PathVariable Integer userId,
                                                                 @PathVariable Integer testListId,
                                                                 @Valid @RequestBody StudentAnswer studentAnswer) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    studentAnswer.setTestList(testList);
                    return studentAnswerRepository.save(studentAnswer);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @PostMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/studentAnswers")
    public StudentAnswer addStudentAnswerByUserAndGroupToSchedule(@PathVariable Integer groupId,
                                                                  @PathVariable Integer userId,
                                                                  @PathVariable Integer testListId,
                                                                  @Valid @RequestBody StudentAnswer studentAnswer) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return testListRepository.findById(testListId)
                .map(testList -> {
                    studentAnswer.setTestList(testList);
                    return studentAnswerRepository.save(studentAnswer);
                }).orElseThrow(() -> new ResourceNotFoundException("TestList not found with id " + testListId));
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}/studentAnswers/{studentAnswerId}")
    public ResponseEntity<?> deleteStudentAnswerByQuestionAndAnswer(@PathVariable Integer questionId,
                                                                    @PathVariable Integer answerId,
                                                                    @PathVariable Integer studentAnswerId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
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

    @DeleteMapping("/roles/{roleId}/users/{userId}/studentAnswers/{studentAnswerId}")
    public ResponseEntity<?> deleteStudentAnswerByUserAndRole(@PathVariable Integer roleId,
                                                              @PathVariable Integer userId,
                                                              @PathVariable Integer studentAnswerId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return studentAnswerRepository.findById(studentAnswerId)
                .map(studentAnswer -> {
                    studentAnswerRepository.delete(studentAnswer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("StudentAnswer not found with id " + studentAnswerId));
    }

    @DeleteMapping("/groups/{groupId}/users/{userId}/studentAnswers/{studentAnswerId}")
    public ResponseEntity<?> deleteStudentAnswerByUserAndGroup(@PathVariable Integer groupId,
                                                               @PathVariable Integer userId,
                                                               @PathVariable Integer studentAnswerId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return studentAnswerRepository.findById(studentAnswerId)
                .map(studentAnswer -> {
                    studentAnswerRepository.delete(studentAnswer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("StudentAnswer not found with id " + studentAnswerId));
    }

    @DeleteMapping("/subject/{subjectId}/testLists/{testListId}/studentAnswers/{studentAnswerId}")
    public ResponseEntity<?> deleteStudentAnswerByTestListAndSubject(@PathVariable Integer subjectId,
                                                                     @PathVariable Integer testListId,
                                                                     @PathVariable Integer studentAnswerId) {
        if (!subjectRepository.existsById(subjectId)) {
            throw new ResourceNotFoundException("Subject not found with id " + subjectId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return studentAnswerRepository.findById(studentAnswerId)
                .map(studentAnswer -> {
                    studentAnswerRepository.delete(studentAnswer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("StudentAnswer not found with id " + studentAnswerId));
    }

    @DeleteMapping("/roles/{roleId}/users/{userId}/testLists/{testListId}/studentAnswers/{studentAnswerId}")
    public ResponseEntity<?> deleteStudentAnswerByTestListWithUserAndRole(@PathVariable Integer roleId,
                                                                          @PathVariable Integer userId,
                                                                          @PathVariable Integer testListId,
                                                                          @PathVariable Integer studentAnswerId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return studentAnswerRepository.findById(studentAnswerId)
                .map(studentAnswer -> {
                    studentAnswerRepository.delete(studentAnswer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("StudentAnswer not found with id " + studentAnswerId));
    }

    @DeleteMapping("/groups/{groupId}/users/{userId}/testLists/{testListId}/studentAnswers/{studentAnswerId}")
    public ResponseEntity<?> deleteStudentAnswerByTestListWithUserAndGroup(@PathVariable Integer groupId,
                                                                           @PathVariable Integer userId,
                                                                           @PathVariable Integer testListId,
                                                                           @PathVariable Integer studentAnswerId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        if (!testListRepository.existsById(testListId)) {
            throw new ResourceNotFoundException("TestList not found with id " + testListId);
        }
        return studentAnswerRepository.findById(studentAnswerId)
                .map(studentAnswer -> {
                    studentAnswerRepository.delete(studentAnswer);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("StudentAnswer not found with id " + studentAnswerId));
    }
}