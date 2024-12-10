package com.pontedegeracoes.api.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pontedegeracoes.api.entitys.Necessity;

@SpringBootTest
public class NecessityEntityTest {
    @Test
    void constructUserEntity(){
        String name = "Tecnologia";

        String expectedString = "{" +
            "name='" + name + "'" +
            "}";

        Necessity newNecessity = new Necessity(name);
        String actualString = newNecessity.toString();

        //verificando se os atributos foram instanciados de forma correta
        assertEquals(expectedString, actualString);
    }
}
