package org.bakanov.spring_tests.controller;

import org.bakanov.spring_tests.exceptions.ResourceNotFoundException;
import org.bakanov.spring_tests.model.Role;
import org.bakanov.spring_tests.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/roles")
    public Page<Role> getRoles(Pageable pageable) { return roleRepository.findAll(pageable); }

    @PostMapping("/roles")
    public Role createdRole(@Valid @RequestBody Role role) { return roleRepository.save(role); }

    @PutMapping("/roles/{roleId}")
    public Role updateRole(@PathVariable Integer roleId,
                           @Valid @RequestBody Role roleRequest) {
        return roleRepository.findById(roleId)
                .map(role -> {
                    role.setName(roleRequest.getName());
                    return roleRepository.save(role);
                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }

    @DeleteMapping("roles/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer roleId) {
        return roleRepository.findById(roleId)
                .map(role -> {
                    roleRepository.delete(role);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
    }
}
