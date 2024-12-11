package com.pontedegeracoes.api.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pontedegeracoes.api.entitys.Meeting;
import com.pontedegeracoes.api.entitys.User;

@SpringBootTest
public class MeetingEntityTest {
    @Test
    void constructMeetingEntity(){
        String city = "sao paulo";
        String state = "sp";
        boolean inPerson = true;
        String description = "Encontro para discutir sobre crochê e amigurumi";
        User person1 = new User("theo", 21, "voluntario", "theo@theo.com", "theozin123", "remoto", "araxa", "mg", List.of("conversa", "tecnologia"));
        User person2 = new User("laura", 98, "idoso", "laura@laura.com", "laurinha123", "presencial", "sao jose do rio preto", "sp", List.of("esporte", "tecnologia"));

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
            "city='" + city + "'\n" +
            "stateInitials='" + state + "'\n" +
            "inPerson='" + inPerson + "'\n" +
            "description='" + description + "'\n" +
            "participants='" + participants + "'\n" +
            "}";

        Meeting newMeeting = new Meeting(date, city, state, inPerson, description, participants);
        String actualString = newMeeting.toString();

        //verificando se os atributos foram instanciados de forma correta
        assertEquals(expectedString, actualString);
    }
}
