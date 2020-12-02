package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.User;
import org.bakanov.spring_tests.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<User> getQuestions(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @PostMapping("/users")
    public User createdQuestion(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/users/{userId}")
    public User updateQuestion(@PathVariable Integer userId, @Valid @RequestBody User userRequest) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirst_name(userRequest.getFirst_name());
                    user.setLast_name(userRequest.getLast_name());
                    user.setMiddle_name(userRequest.getMiddle_name());
                    user.setLogin(userRequest.getLogin());
                    user.setPassword_hash(userRequest.getPassword_hash());
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Integer userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
