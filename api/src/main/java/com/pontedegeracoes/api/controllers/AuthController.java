package com.pontedegeracoes.api.controllers;

import com.pontedegeracoes.api.dtos.user.UserCreateDTO;
import com.pontedegeracoes.api.dtos.user.UserDTO;
import com.pontedegeracoes.api.dtos.user.UserUpdateDTO;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.mappers.UserMapper;
import com.pontedegeracoes.api.services.JwtService;
import com.pontedegeracoes.api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserService userService;
  private final UserMapper userMapper;

  public AuthController(AuthenticationManager authenticationManager,
      JwtService jwtService,
      UserService userService,
      UserMapper userMapper) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    String token = jwtService.generateToken(request.email());
    User user = userService.getUserFromToken(token);

    return ResponseEntity.ok(new AuthResponse(token, userMapper.toDTO(user)));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody UserCreateDTO user) {
    User registeredUser = userService.registerUser(user);
    String token = jwtService.generateToken(user.email());

    return ResponseEntity.ok(new AuthResponse(token, userMapper.toDTO(registeredUser)));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<UserDTO> updateUser(
      @PathVariable Long id,
      @Valid @RequestBody UserUpdateDTO userDTO) {
    User updatedUser = userService.updateUser(id, userDTO);
    return ResponseEntity.ok(userMapper.toDTO(updatedUser));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}

record LoginRequest(String email, String password) {
}

record AuthResponse(String token, UserDTO user) {
}