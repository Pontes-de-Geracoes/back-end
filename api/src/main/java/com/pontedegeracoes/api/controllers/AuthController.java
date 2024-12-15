package com.pontedegeracoes.api.controllers;

import com.pontedegeracoes.api.entitys.User;
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

  public AuthController(AuthenticationManager authenticationManager,
      JwtService jwtService,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    String token = jwtService.generateToken(request.email());
    User user = userService.getUserFromToken(token);

    return ResponseEntity.ok(new AuthResponse(token, user));
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody User user) {
    User registeredUser = userService.registerUser(user);
    String token = jwtService.generateToken(user.getEmail());

    return ResponseEntity.ok(new AuthResponse(token, registeredUser));
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id,
      @Valid @RequestBody User userDetails) {
    User updatedUser = userService.updateUser(id, userDetails);
    return ResponseEntity.ok(updatedUser);
  }
}

record LoginRequest(String email, String password) {
}

record AuthResponse(String token, User user) {
}