/*
 * package com.pontedegeracoes.api.entityTest;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals;
 * 
 * import java.util.Set;
 * 
 * import org.junit.jupiter.api.Test;
 * import org.springframework.boot.test.context.SpringBootTest;
 * 
 * import com.pontedegeracoes.api.entitys.Necessity;
 * import com.pontedegeracoes.api.entitys.User;
 * 
 * @SpringBootTest
 * public class UserEntityTest {
 * 
 * @Test
 * void constructUserEntity() {
 * String name = "Maria";
 * String userType = "idoso";
 * String email = "maria@maria.com";
 * String password = "1234";
 * int age = 96;
 * String meetingPreference = "remoto";
 * String city = "sao paulo";
 * String state = "sp";
 * Necessity necessity1 = new Necessity("tecnologia",
 * "Ajuda com mensagens e internet");
 * Set<Necessity> necessities = Set.of(necessity1);
 * 
 * String expectedString = "{\n" +
 * "name='" + name + "'\n" +
 * "age='" + age + "'\n" +
 * "userType='" + userType + "'\n" +
 * "email='" + email + "'\n" +
 * "password='" + password + "'\n" +
 * "meetingPreference='" + meetingPreference + "'\n" +
 * "city='" + city + "'\n" +
 * "stateInitials='" + state + "'\n" +
 * "necessities='" + necessities + "'\n" +
 * "}";
 * 
 * User newUser = new User(name, age, userType, email, password,
 * meetingPreference, city, state, necessities);
 * String actualString = newUser.toString();
 * 
 * // verificando se os atributos foram instanciados de forma correta
 * assertEquals(expectedString, actualString);
 * }
 * }
 */