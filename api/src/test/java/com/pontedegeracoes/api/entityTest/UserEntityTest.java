package com.pontedegeracoes.api.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pontedegeracoes.entitys.User;

@SpringBootTest
public class UserEntityTest {
    @Test
    void constructUserEntity(){
        String name = "Maria";
        String userType = "idoso";
        int age = 96;
        String meetingPreference = "remoto";
        double latitude = 0.3;
        double longitude = 0.3;
        List<String> necessities = List.of("mobilidade", "conversa");
        List<String> hobbies = List.of("croche", "palavra-cruzada");
        
        String expectedString = "{\n" +
            "name='" + name + "'\n" +
            "age='" + age + "'\n" +
            "userType='" + userType + "'\n" +
            "meetingPreference='" + meetingPreference + "'\n" +
            "latitude='" + latitude + "'\n" +
            "longitude='" + longitude + "'\n" +
            "necessities='" + necessities + "'\n" +
            "hobbies='" + hobbies + "'\n" +
            "}";

        User newUser = new User(name, age, userType, meetingPreference, latitude, longitude, necessities, hobbies);
        String actualString = newUser.toString();

        //verificando se os atributos foram instanciados de forma correta
        assertEquals(expectedString, actualString);
    }
}
