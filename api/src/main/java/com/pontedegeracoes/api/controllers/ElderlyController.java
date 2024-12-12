package com.pontedegeracoes.api.controllers;

import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositorys.NecessityRepository;
import com.pontedegeracoes.api.repositorys.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("elderly")
public class ElderlyController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    NecessityRepository necessityRepository;

    Logger logger = LoggerFactory.getLogger(ElderlyController.class);

    public ElderlyController(){}

    @GetMapping("/list")
    public List<User> getElderly(){
        return userRepository.findByUserType("idoso");
    }

    @GetMapping("/start")
    public String addInitialElderly(){
        Necessity necessity1 = new Necessity("tecnologia", "Ajuda com mensagens e navegação da internet.");
        Necessity necessity2 = new Necessity("esporte", "Ajuda com mobilidade e exercícios físicos.");

        List<Necessity> savedNecessities = necessityRepository.saveAll(List.of(necessity1, necessity2));

        userRepository.save(new User("pedro", 80, "idoso", "pedro@pedro.com", "1234", "remoto", "araxa", "mg", new HashSet<>(savedNecessities)));
        userRepository.save(new User("laura", 98, "idoso", "laura@laura.com", "laurinha123", "presencial", "sao jose do rio preto", "sp", new HashSet<>(savedNecessities)));
        return "Data base successfully started (Elederly)!";
    }

    @GetMapping("/")
    public List<User> getElderlyByName(@Valid @RequestParam String name){
        return userRepository.findByNameAndUserType(name, "idoso");
    }

    @PostMapping()
    public ResponseEntity<User> postNewElderly(@Valid @RequestBody User newUser){

        //analisa as necessidades escolhidas pelo usuario e verifica se
        //elas existem
        boolean validNecessities = newUser.getNecessities().stream()
        .allMatch(necessity -> necessityRepository.existsByName(necessity.getName()));

        logger.debug("\n\n\n\n\n\nNecessidades sao validas: "+ validNecessities);

        if(!validNecessities){
            return ResponseEntity.badRequest().build();
        }
        User savedUser = userRepository.save(newUser);
        return new ResponseEntity<User>(savedUser,HttpStatus.CREATED);
    }
}
