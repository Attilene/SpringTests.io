package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Group;
import org.bakanov.spring_tests.model.User;
import org.bakanov.spring_tests.repository.GroupRepository;
import org.bakanov.spring_tests.repository.RoleRepository;
import org.bakanov.spring_tests.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/roles/{roleId}/users")
    public List<User> getUsersByRoleId(@PathVariable Integer roleId) { return userRepository.findByRoleId(roleId); }

    @GetMapping("/groups/{groupId}/users")
    public List<User> getUsersByGroupId(@PathVariable Integer groupId) { return userRepository.findByGroupId(groupId); }

    @PostMapping("/roles/{roleId}/users")
    public User addRoleToUser(@PathVariable Integer roleId,
                              @Valid @RequestBody User user) {
        return roleRepository.findById(roleId)
                .map(role -> {
                    user.setRole(role);
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }

    @PostMapping("groups/{groupId}/roles/{roleId}/users")
    public User addGroupAndRoleToUser(@PathVariable Integer groupId,
                                      @PathVariable Integer roleId,
                                      @Valid @RequestBody User user) {
        Group group;
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        else { group = groupRepository.getOne(groupId); }
        return roleRepository.findById(roleId)
                .map(role -> {
                    user.setRole(role);
                    user.setGroup(group);
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }

    @PutMapping("/roles/{roleId}/users/{userId}")
    public User updateUserAndRole(@PathVariable Integer roleId,
                                  @PathVariable Integer userId,
                                  @Valid @RequestBody User userRequest){
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
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

    @PutMapping("groups/{groupId}/roles/{roleId}/users/{userId}")
    public User updateUserAndGroup(@PathVariable Integer groupId,
                                   @PathVariable Integer roleId,
                                   @PathVariable Integer userId,
                                   @Valid @RequestBody User userRequest){
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
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

    @DeleteMapping("/roles/{roleId}/users/{userId}")
    public ResponseEntity<?> deleteUserByRole(@PathVariable Integer roleId,
                                              @PathVariable Integer userId) {
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }

    @DeleteMapping("groups/{groupId}/roles/{roleId}/users/{userId}")
    public ResponseEntity<?> deleteUserByGroup(@PathVariable Integer groupId,
                                               @PathVariable Integer roleId,
                                               @PathVariable Integer userId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id " + groupId);
        }
        if (!roleRepository.existsById(roleId)) {
            throw new ResourceNotFoundException("Role not found with id " + roleId);
        }
        return userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}
