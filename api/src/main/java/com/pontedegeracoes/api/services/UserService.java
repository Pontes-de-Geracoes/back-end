package com.pontedegeracoes.api.services;

import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositories.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  @Transactional
  public User registerUser(User user) {
    // Check if user already exists
    if (userRepository.findByEmailIgnoreCase(user.getEmail()).isPresent()) {
      throw new RuntimeException("Email already registered");
    }

    // Encode password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Set default photo if not provided
    if (user.getPhoto() == null || user.getPhoto().isEmpty()) {
      user.setPhoto("https://pic.onlinewebfonts.com/thumbnails/icons_23485.svg");
    }

    return userRepository.save(user);
  }

  @Transactional
  public User updateUser(Long id, User userDetails) {
    User currentUser = getCurrentUser();

    // Only allow users to update their own profile
    if (currentUser.getId() != id) {
      throw new RuntimeException("You can only update your own profile");
    }

    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    // Update fields if provided
    if (userDetails.getName() != null) {
      user.setName(userDetails.getName());
    }
    if (userDetails.getAge() != 0) {
      user.setAge(userDetails.getAge());
    }
    if (userDetails.getType() != null) {
      user.setType(userDetails.getType());
    }
    if (userDetails.getPhoto() != null) {
      user.setPhoto(userDetails.getPhoto());
    }
    if (userDetails.getEmail() != null) {
      user.setEmail(userDetails.getEmail());
    }
    if (userDetails.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
    }
    if (userDetails.getMeetingPreference() != null) {
      user.setMeetingPreference(userDetails.getMeetingPreference());
    }
    if (userDetails.getTown() != null) {
      user.setTown(userDetails.getTown());
    }
    if (userDetails.getState() != null) {
      user.setState(userDetails.getState());
    }

    return userRepository.save(user);
  }

  public User getCurrentUser() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
        .getAuthentication()
        .getPrincipal();

    return userRepository.findByEmailIgnoreCase(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

  public User getUserFromToken(String token) {
    String email = jwtService.extractUsername(token.replace("Bearer ", ""));
    return userRepository.findByEmailIgnoreCase(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
  }
}