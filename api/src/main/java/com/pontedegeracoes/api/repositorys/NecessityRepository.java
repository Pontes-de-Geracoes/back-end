package com.pontedegeracoes.api.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pontedegeracoes.api.entitys.Necessity;

public interface NecessityRepository extends JpaRepository<Necessity, Long>{
    List<Necessity> findAll();
    boolean existsByName(String name);

}
