package com.pontedegeracoes.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontedegeracoes.api.entitys.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String name);

    List<User> findAllByNameContainingIgnoreCase(String name);

    List<User> findAllByType(String type);
}