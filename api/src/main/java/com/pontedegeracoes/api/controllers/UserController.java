package com.pontedegeracoes.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
@RequestMapping("user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    public UserController(){}

    @GetMapping("/")
    public String getWelcome(){
        return "Welcome to the User Controller!";
    }

    @GetMapping("/list-users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/start-database")
    public String addInitialUsers(){
        userRepository.save(new User("laura", 68, "idoso", "presencial", 0.3, 0.3, List.of("caminhar", "tecnologia") , List.of("croche", "leitura")));
        userRepository.save(new User("vitor", 20, "voluntario", "presencial", 0.3, 0.3, List.of("esporte", "tecnologia") , List.of("filmes", "leitura")));
        return "Data base successfully started!";
    }

    @GetMapping("/get-user")
    public User getUserByName(@RequestParam String name){
        return userRepository.findByName(name);
    }

    @PostMapping("/newUser")
    public ResponseEntity<User> postNewUser(@RequestBody User newUser){
        User savedUser = userRepository.save(newUser);
        return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
    }
}
