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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class VolunteerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetVolunteers() throws Exception{
        this.mockMvc.perform(get("/volunteer/list"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[*].userType", everyItem(equalTo("voluntario"))));
    }

    @Test
    void testGetVolunteerByName() throws Exception{
        String nameParam = "vitor";
        this.mockMvc.perform(get("/volunteer/").param("name", nameParam))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[*].name", everyItem(equalTo(nameParam))));
    }

    @Test
    void testPostVolunteer() throws Exception{
        String requestBody = "{\"userId\": 2,\"name\": \"theo\",\"age\": 21,\"userType\": \"voluntario\",\"meetingPreference\": \"remoto\",\"latitude\": 1.3,\"longitude\": 5.3,\"necessities\": [\"mobilidade\",\"tecnologia\"]}";
        this.mockMvc.perform(post("/volunteer").content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.userType", equalTo("voluntario")));
    }
}
