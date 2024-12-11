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

import jakarta.validation.Valid;

@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    @Autowired
    UserRepository userRepository;

    public VolunteerController(){}

    @GetMapping("/list")
    public List<User> getVolunteers(){
        return userRepository.findByUserType("voluntario");
    }

    @GetMapping("/start")
    public String addInitialVolunteers(){
        userRepository.save(new User("theo", 21, "voluntario", "theo@theo.com", "theozin123", "remoto", "araxa", "mg", List.of("conversa", "tecnologia")));
        userRepository.save(new User("vitor", 20, "voluntario", "vitor@vitor.com", "vitor123", "presencial", "sao paulo", "sp", List.of("esporte", "tecnologia")));
        return "Data base successfully started! (Volunteer)";
    }

    @GetMapping("/")
    public List<User> getVolunteerByName(@Valid @RequestParam String name){
        return userRepository.findByNameAndUserType(name, "voluntario");
    }

    @PostMapping()
    public ResponseEntity<User> postNewVolunteer(@Valid @RequestBody User newUser){
        User savedUser = userRepository.save(newUser);
        return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
    }
}
