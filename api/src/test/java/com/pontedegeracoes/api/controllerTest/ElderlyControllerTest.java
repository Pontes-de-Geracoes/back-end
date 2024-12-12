package com.pontedegeracoes.api.controllerTest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositorys.UserRepository;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ElderlyControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    /*@Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void start(){
        userRepository.deleteAll();
        userRepository.save(new User("pedro", 80, "idoso", "pedro@pedro.com", "1234", "remoto", "araxa", "mg", List.of("conversa", "tecnologia")));
        userRepository.save(new User("laura", 98, "idoso", "laura@laura.com", "laurinha123", "presencial", "sao jose do rio preto", "sp", List.of("esporte", "tecnologia")));
    }*/

    //TODO:adicionar registros no construtor
    @Test
    void testGetElderly() throws Exception{
        this.mockMvc.perform(get("/elderly/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[*].userType", everyItem(equalTo("idoso"))));
    }

    @Test
    void testGetElderlyByName() throws Exception{
        String nameParam = "laura";
        this.mockMvc.perform(get("/elderly/").param("name", nameParam))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[*].name", everyItem(equalTo(nameParam))));
    }

    @Test
    void testPostElderly() throws Exception{
        String requestBody = "{\"userId\": 2,\"name\": \"joao\",\"age\": 81,\"userType\": \"idoso\",\"email\": \"pedro@pedro.com\",\"password\": \"1234\",\"meetingPreference\": \"remoto\",\"city\": \"sao paulo\",\"stateInitials\": \"sp\",\"necessity\": [\"mobilidade\",\"tecnologia\"]}";
        this.mockMvc.perform(post("/elderly").content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userType", equalTo("idoso")));
    }
}
