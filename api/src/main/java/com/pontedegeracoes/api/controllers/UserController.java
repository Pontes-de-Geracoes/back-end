package com.pontedegeracoes.api.controllers;

import com.pontedegeracoes.api.dtos.user.UserDTO;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.mappers.UserMapper;
import com.pontedegeracoes.api.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(userMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDTO>> getUserByName(@PathVariable String name) {
        List<User> users = userRepository.findAllByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(users.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .map(user -> ResponseEntity.ok(userMapper.toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/volunteers")
    public ResponseEntity<List<UserDTO>> getVolunteers() {
        List<User> volunteers = userRepository.findAllByType("volunteer");
        return ResponseEntity.ok(volunteers.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/elderly")
    public ResponseEntity<List<UserDTO>> getElderly() {
        List<User> elderly = userRepository.findAllByType("elderly");
        return ResponseEntity.ok(elderly.stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
    }

}