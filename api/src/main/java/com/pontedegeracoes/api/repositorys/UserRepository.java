package com.pontedegeracoes.api.repositorys;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pontedegeracoes.api.entitys.User;

public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findAll();
    void deleteByName(String name);
    User findByName(String name);
} 