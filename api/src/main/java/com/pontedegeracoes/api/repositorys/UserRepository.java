package com.pontedegeracoes.api.repositorys;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pontedegeracoes.api.entitys.User;

public interface UserRepository extends CrudRepository<User, Long>{
    List<User> findAll();
    List<User> findByUserType(String userType);
    void deleteByName(String name);
    List<User> findByNameAndUserType(String name, String userType);
    List<User> findByName(String name);
} 