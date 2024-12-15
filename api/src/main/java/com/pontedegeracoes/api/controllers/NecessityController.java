package com.pontedegeracoes.api.controllers;

import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.repositories.NecessityRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/necessities")
public class NecessityController {

  @Autowired
  private NecessityRepository necessityRepository;

  @GetMapping
  public ResponseEntity<List<Necessity>> getAllNecessities() {
    List<Necessity> necessities = necessityRepository.findAll();
    return ResponseEntity.ok(necessities);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Necessity> getNecessityById(@PathVariable Long id) {
    Optional<Necessity> necessity = necessityRepository.findById(id);
    return necessity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Necessity> createNecessity(@Valid @RequestBody Necessity necessity) {

    Optional<Necessity> necessityExists = necessityRepository.findByNameIgnoreCase(necessity.getName());
    if (necessityExists.isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    Necessity savedNecessity = necessityRepository.save(necessity);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedNecessity);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Necessity> updateNecessity(@PathVariable Long id,
      @Valid @RequestBody Necessity necessityDetails) {
    Optional<Necessity> necessity = necessityRepository.findById(id);

    return necessity.map(existingNecessity -> {

      existingNecessity.setName(necessityDetails.getName());
      Necessity updatedNecessity = necessityRepository.save(existingNecessity);
      return ResponseEntity.ok(updatedNecessity);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNecessity(@PathVariable Long id) {
    Optional<Necessity> necessity = necessityRepository.findById(id);
    if (necessity.isPresent()) {
      necessityRepository.delete(necessity.get());
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}