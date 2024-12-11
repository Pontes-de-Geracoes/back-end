package com.pontedegeracoes.api.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ElderlyControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

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
