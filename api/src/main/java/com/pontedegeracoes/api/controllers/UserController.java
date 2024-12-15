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
    public ResponseEntity<List<User>> getUserByName(@PathVariable String name) {
        List<User> user = userRepository.findAllByNameContainingIgnoreCase(name);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
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

        /* Check if the user already with this email already exist */
        Optional<User> userExists = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (userExists.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Set<Necessity> existingNecessities = new HashSet<>();
        for (Necessity necessity : user.getNecessities()) {
            Optional<Necessity> existingNecessity = necessityRepository.findById(necessity.getId());
            if (existingNecessity.isPresent()) {
                existingNecessities.add(existingNecessity.get());
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Necessity not found ID: " + necessity.getId(),
                        null);
            }
        }

        user.setNecessities(existingNecessities);

        user.setPhoto("https://pic.onlinewebfonts.com/thumbnails/icons_23485.svg");
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User userDetails) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setName(userDetails.getName() != null ? userDetails.getName() : updatedUser.getName());
            updatedUser.setAge(userDetails.getAge() != 0 ? userDetails.getAge() : updatedUser.getAge());
            updatedUser.setType(userDetails.getType() != null ? userDetails.getType() : updatedUser.getType());
            updatedUser.setPhoto(
                    userDetails.getPhoto() != null ? userDetails.getPhoto() : updatedUser.getPhoto());
            updatedUser.setEmail(userDetails.getEmail() != null ? userDetails.getEmail() : updatedUser.getEmail());
            updatedUser.setPassword(
                    userDetails.getPassword() != null ? userDetails.getPassword() : updatedUser.getPassword());
            updatedUser.setMeetingPreference(
                    userDetails.getMeetingPreference() != null ? userDetails.getMeetingPreference()
                            : updatedUser.getMeetingPreference());
            updatedUser.setTown(userDetails.getTown() != null ? userDetails.getTown() : updatedUser.getTown());
            updatedUser.setState(userDetails.getState() != null ? userDetails.getState() : updatedUser.getState());
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
