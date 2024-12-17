package com.pontedegeracoes.api.controllers;

import com.pontedegeracoes.api.dtos.user.UserCreateDTO;
import com.pontedegeracoes.api.dtos.user.UserDTO;
import com.pontedegeracoes.api.dtos.user.UserUpdateDTO;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.mappers.UserMapper;
import com.pontedegeracoes.api.services.JwtService;
import com.pontedegeracoes.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
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

  @Operation(summary = "Login user", description = "Authenticate user and return JWT token", security = {})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
      @ApiResponse(responseCode = "401", description = "Invalid credentials")
  })
  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    String token = jwtService.generateToken(request.email());
    User user = userService.getUserFromToken(token);

    return ResponseEntity.ok(new AuthResponse(token, userMapper.toDTO(user)));
  }

  @Operation(summary = "Register new user", description = "Create new user account", security = {})
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

  @Operation(summary = "Get user profile", description = "Get user profile from JWT token")
  @GetMapping("/profile")
  public ResponseEntity<UserDTO> getProfile(
      @Parameter(hidden = true) @RequestHeader("Authorization") String authHeader) {
    try {
      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String token = authHeader.substring(7);
      User user = userService.getUserFromToken(token);
      return ResponseEntity.ok(userMapper.toDTO(user));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}

@Schema(description = "Login request payload")
record LoginRequest(
    @Schema(example = "maria@email.com") String email,

    @Schema(example = "password123") String password) {
}

record AuthResponse(String token, UserDTO user) {
}