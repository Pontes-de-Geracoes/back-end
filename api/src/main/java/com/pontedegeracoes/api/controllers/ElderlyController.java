package com.pontedegeracoes.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositorys.UserRepository;

@RestController
@RequestMapping("elderly")
public class ElderlyController {
    @Autowired
    UserRepository userRepository;

    public ElderlyController(){}

    @GetMapping("/list")
    public List<User> getElderly(){
        return userRepository.findByUserType("idoso");
    }

    @GetMapping("/start")
    public String addInitialElderly(){
        userRepository.save(new User("laura", 98, "idoso", "remoto", 7.1, 3.5, List.of("caminhada", "tecnologia")));
        userRepository.save(new User("pedro", 80, "idoso", "presencial", 0.3, 0.3, List.of("esporte", "conversa")));
        return "Data base successfully started (Elederly)!";
    }

    @GetMapping("/")
    public List<User> getElderlyByName(@RequestParam String name){
        return userRepository.findByNameAndUserType(name, "idoso");
    }

    @PostMapping()
    public ResponseEntity<User> postNewElderly(@RequestBody User newUser){
        User savedUser = userRepository.save(newUser);
        return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
    }
}
