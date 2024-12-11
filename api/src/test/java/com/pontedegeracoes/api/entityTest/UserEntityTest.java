package com.pontedegeracoes.api.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pontedegeracoes.api.entitys.User;

@SpringBootTest
public class UserEntityTest {
    @Test
    void constructUserEntity(){
        String name = "Maria";
        String userType = "idoso";
        String email = "maria@maria.com";
        String password = "1234";
        int age = 96;
        String meetingPreference = "remoto";
        String city = "sao paulo";
        String state = "sp";
        List<String> necessities = List.of("mobilidade", "conversa");
    
        String expectedString = "{\n" +
            "name='" + name + "'\n" +
            "age='" + age + "'\n" +
            "userType='" + userType + "'\n" +
            "email='" + email + "'\n" +
            "password='" + password + "'\n" +
            "meetingPreference='" + meetingPreference + "'\n" +
            "city='" + city + "'\n" +
            "stateInitials='" + state + "'\n" +
            "necessities='" + necessities + "'\n" +
            "}";

        User newUser = new User(name, age, userType, email, password, meetingPreference, city, state, necessities);
        String actualString = newUser.toString();

        //verificando se os atributos foram instanciados de forma correta
        assertEquals(expectedString, actualString);
    }
}
