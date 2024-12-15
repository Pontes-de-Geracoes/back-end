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
        String description = "Precisa de ajuda para enviar mensagens e navegar na internet";

        String expectedString = "{\n" +
            "name='" + name + "'\n" +
            "description='" + description + "'\n" +
            "}";

        Necessity newNecessity = new Necessity(name, description);
        String actualString = newNecessity.toString();

        //verificando se os atributos foram instanciados de forma correta
        assertEquals(expectedString, actualString);
    }
}
