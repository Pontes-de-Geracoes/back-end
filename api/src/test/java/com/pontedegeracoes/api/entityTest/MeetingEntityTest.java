package com.pontedegeracoes.api.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pontedegeracoes.entitys.Meeting;
import com.pontedegeracoes.entitys.User;

@SpringBootTest
public class MeetingEntityTest {
    @Test
    void constructMeetingEntity(){
        double latitude = 0.3;
        double longitude = 0.3;
        boolean inPerson = true;
        String description = "Encontro para discutir sobre crochê e amigurumi";

        User person1 = new User("Laura", 68, "idoso", "presencial", 0.3, 0.3, List.of("caminhar", "tecnologia") , List.of("croche", "leitura"));
        User person2 = new User("Vítor", 20, "voluntario", "presencial", 0.3, 0.3, List.of("esporte", "tecnologia") , List.of("filmes", "leitura"));

        List<User> participants = List.of(person1, person2);
        Date date = null;
        try{
            date = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss").parse("09/12/2024 10:00:00");
        }catch(Exception e){
            //teste sempre deve falhar
            assertEquals(0, 1);
        }

        String expectedString = "{\n" +
            "date='" + date + "'\n" +
            "latitude='" + latitude + "'\n" +
            "longitude='" + longitude + "'\n" +
            "inPerson='" + inPerson + "'\n" +
            "description='" + description + "'\n" +
            "participants='" + participants + "'\n" +
            "}";

        Meeting newMeeting = new Meeting(date, latitude, longitude, inPerson, description, participants);
        String actualString = newMeeting.toString();

        //verificando se os atributos foram instanciados de forma correta
        assertEquals(expectedString, actualString);
    }
}
