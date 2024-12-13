package com.pontedegeracoes.api.entityTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.pontedegeracoes.api.entitys.Meeting;
import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.entitys.User;

@SpringBootTest
public class MeetingEntityTest {
    @Test
    void constructMeetingEntity(){
        String city = "sao paulo";
        String state = "sp";
        boolean inPerson = true;
        String description = "Encontro para discutir sobre crochê e amigurumi";
        
        Necessity necessity1 = new Necessity("tecnologia", "Ajuda com mensagens e internet");
        User person1 = new User("theo", 21, "voluntario", "theo@theo.com", "theozin123", "remoto", "araxa", "mg", Set.of(necessity1));
        User person2 = new User("laura", 98, "idoso", "laura@laura.com", "laurinha123", "presencial", "sao jose do rio preto", "sp", Set.of(necessity1));

        Set<User> participants = Set.of(person1, person2);
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
