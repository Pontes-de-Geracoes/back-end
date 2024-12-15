package com.pontedegeracoes.api.services;

import com.pontedegeracoes.api.dtos.user.UserCreateDTO;
import com.pontedegeracoes.api.dtos.user.UserDTO;
import com.pontedegeracoes.api.dtos.user.UserUpdateDTO;
import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.mappers.UserMapper;
import com.pontedegeracoes.api.repositories.NecessityRepository;
import com.pontedegeracoes.api.repositories.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

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
  private final NecessityRepository necessityRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      NecessityRepository necessityRepository,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.necessityRepository = necessityRepository;
    this.userMapper = userMapper;
  }

  @Transactional
  public User registerUser(UserCreateDTO userDTO) {
    // Check if user already exists
    if (userRepository.findByEmailIgnoreCase(userDTO.email()).isPresent()) {
      throw new RuntimeException("Email already registered");
    }

    Set<Necessity> necessities = userDTO.necessities().stream()
        .map(id -> necessityRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Necessity not found: " + id)))
        .collect(Collectors.toSet());

    User user = userMapper.toEntity(userDTO);
    user.setNecessities(necessities);
    user.setPassword(passwordEncoder.encode(userDTO.password()));

    // Encode password
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Set default photo if not provided
    if (user.getPhoto() == null || user.getPhoto().isEmpty()) {
      user.setPhoto("https://pic.onlinewebfonts.com/thumbnails/icons_23485.svg");
    }

    return userRepository.save(user);
  }

  @Transactional
  public User updateUser(Long id, UserUpdateDTO userDTO) {
    User currentUser = getCurrentUser();

    if (currentUser.getId() != id) {
      throw new RuntimeException("You can only update your own profile");
    }

    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (userDTO.necessities() != null && !userDTO.necessities().isEmpty()) {
      Set<Necessity> necessities = userDTO.necessities().stream()
          .map(necessityId -> necessityRepository.findById(necessityId)
              .orElseThrow(() -> new RuntimeException("Necessity not found: " + necessityId)))
          .collect(Collectors.toSet());
      user.setNecessities(necessities);
    }

    userMapper.updateEntityFromDTO(userDTO, user);

    return userRepository.save(user);
  }

  @Transactional
  public void deleteUser(Long id) {
    User currentUser = getCurrentUser();

    if (currentUser.getId() != id) {
      throw new RuntimeException("You can only delete your own account");
    }

    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found"));

    userRepository.delete(user);
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