package com.pontedegeracoes.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.repositorys.NecessityRepository;

@RestController
@RequestMapping("necessity")
public class NecessityController {
    @Autowired
    NecessityRepository necessityRepository;

    public NecessityController(){}

    @GetMapping("/list") 
    public List<Necessity> getNecessity(){
        return necessityRepository.findAll();
    }

    @GetMapping("/start")
    public String addInitialNecessity(){
        necessityRepository.save(new Necessity("esporte", "Ajuda em exercício físico."));
        necessityRepository.save(new Necessity("tecnologia", "Ajuda com mensagens e navegação da internet."));
        return "Data base successfully started (Necessity)!";
    }
}
