package com.pontedegeracoes.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontedegeracoes.api.entitys.Necessity;

public interface NecessityRepository extends JpaRepository<Necessity, Long> {

  public Optional<Necessity> findByNameIgnoreCase(String name);
}
