package com.pontedegeracoes.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontedegeracoes.api.entitys.Necessity;

public interface NecessityRepository extends JpaRepository<Necessity, Long> {
  Optional<Necessity> findByName(String name);

}
