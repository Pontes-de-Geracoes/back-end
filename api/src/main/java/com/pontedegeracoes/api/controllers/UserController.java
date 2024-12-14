package com.pontedegeracoes.api.controllers;

import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositories.NecessityRepository;
import com.pontedegeracoes.api.repositories.UserRepository;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NecessityRepository necessityRepository;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        Optional<User> user = userRepository.findByName(name);
        return user
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/volunteers")
    public ResponseEntity<List<User>> getVolunteers() {
        List<User> volunteers = userRepository.findAllByType("volunteer");
        return ResponseEntity.ok(volunteers);
    }

    @GetMapping("/elderly")
    public ResponseEntity<List<User>> getElderly() {
        List<User> elderly = userRepository.findAllByType("elderly");
        return ResponseEntity.ok(elderly);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        // Find existing necessities by name and set them
        Set<Necessity> existingNecessities = new HashSet<>();
        for (Necessity necessity : user.getNecessities()) {
            Optional<Necessity> existingNecessity =
                necessityRepository.findByName(necessity.getName());
            if (existingNecessity.isPresent()) {
                existingNecessities.add(existingNecessity.get());
            } else {
                throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Necessity not found: " + necessity.getName(),
                    null
                );
            }
        }
        user.setNecessities(existingNecessities);

        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody User userDetails
    ) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setName(userDetails.getName());
            updatedUser.setAge(userDetails.getAge());
            updatedUser.setType(userDetails.getType());
            updatedUser.setEmail(userDetails.getEmail());
            updatedUser.setPassword(userDetails.getPassword());
            updatedUser.setMeetingPreference(
                userDetails.getMeetingPreference()
            );
            updatedUser.setTown(userDetails.getTown());
            updatedUser.setState(userDetails.getState());
            return ResponseEntity.ok(userRepository.save(updatedUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
