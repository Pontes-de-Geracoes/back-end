package com.pontedegeracoes.api.config;

import com.pontedegeracoes.api.entitys.*;
import com.pontedegeracoes.api.repositories.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component
public class DatabaseSeeder implements ApplicationListener<ApplicationReadyEvent> {
        private static final Logger logger = LoggerFactory.getLogger(DatabaseSeeder.class);

        private final UserRepository userRepository;
        private final NecessityRepository necessityRepository;
        private final MeetingRepository meetingRepository;
        private final EntityManager entityManager;
        private final PasswordEncoder passwordEncoder;

        public DatabaseSeeder(UserRepository userRepository,
                        NecessityRepository necessityRepository,
                        MeetingRepository meetingRepository,
                        EntityManager entityManager,
                        PasswordEncoder passwordEncoder) {
                this.userRepository = userRepository;
                this.necessityRepository = necessityRepository;
                this.meetingRepository = meetingRepository;
                this.entityManager = entityManager;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        @Transactional
        public void onApplicationEvent(ApplicationReadyEvent event) {
                try {
                        if (isDatabaseEmpty()) {
                                seedDatabase();
                        }
                } catch (Exception e) {
                        logger.error("Error seeding database: ", e);
                }
        }

        private boolean isDatabaseEmpty() {
                return userRepository.count() == 0;
        }

        @Transactional
        protected void seedDatabase() {
                try {
                        // 1. Create and save necessities first
                        List<Necessity> necessities = Arrays.asList(
                                        new Necessity("Caminhar", "Walking in the park or around the neighborhood"),
                                        new Necessity("Cantar", "Singing songs or karaoke"),
                                        new Necessity("Dançar", "Dancing to music"),
                                        new Necessity("Tecnologia", "Learning and using technology"));
                        necessityRepository.saveAll(necessities);
                        entityManager.flush();

                        // 2. Create users with proper authentication
                        User elderly1 = new User();
                        elderly1.setName("Maria Silva");
                        elderly1.setAge(75);
                        elderly1.setType("elderly");
                        elderly1.setEmail("maria@email.com");
                        elderly1.setPassword(passwordEncoder.encode("password123"));
                        elderly1.setMeetingPreference("in person");
                        elderly1.setTown("São Paulo");
                        elderly1.setState("SP");
                        elderly1.setPhoto((""));

                        User elderly2 = new User();
                        elderly2.setName("João Santos");
                        elderly2.setAge(80);
                        elderly2.setType("elderly");
                        elderly2.setEmail("joao@email.com");
                        elderly2.setPassword(passwordEncoder.encode("password123"));
                        elderly2.setMeetingPreference("remote");
                        elderly2.setTown("Rio de Janeiro");
                        elderly2.setState("RJ");
                        elderly2.setPhoto((""));

                        User volunteer1 = new User();
                        volunteer1.setName("Ana Pereira");
                        volunteer1.setAge(25);
                        volunteer1.setType("volunteer");
                        volunteer1.setEmail("ana@email.com");
                        volunteer1.setPassword(passwordEncoder.encode("password123"));
                        volunteer1.setMeetingPreference("hybrid");
                        volunteer1.setTown("Curitiba");
                        volunteer1.setState("PR");
                        volunteer1.setPhoto("");

                        User volunteer2 = new User();
                        volunteer2.setName("Pedro Costa");
                        volunteer2.setAge(30);
                        volunteer2.setType("volunteer");
                        volunteer2.setEmail("pedro@email.com");
                        volunteer2.setPassword(passwordEncoder.encode("password123"));
                        volunteer2.setMeetingPreference("in person");
                        volunteer2.setTown("Salvador");
                        volunteer2.setState("BA");
                        volunteer2.setPhoto("");

                        // 3. Save users first
                        List<User> users = Arrays.asList(elderly1, elderly2, volunteer1, volunteer2);
                        userRepository.saveAll(users);
                        entityManager.flush();

                        // 4. Assign necessities to users
                        Random random = new Random();
                        List<Necessity> allNecessities = necessityRepository.findAll();

                        for (User user : users) {
                                Set<Necessity> userNecessities = new HashSet<>();
                                userNecessities.add(allNecessities.get(random.nextInt(allNecessities.size())));
                                userNecessities.add(allNecessities.get(random.nextInt(allNecessities.size())));
                                user.setNecessities(userNecessities);
                        }
                        userRepository.saveAll(users);
                        entityManager.flush();

                        // 5. Create and save meetings
                        Meeting meeting2 = new Meeting(
                                        "Caminhada no Parque",
                                        "Caminhada matinal para exercício",
                                        "in person",
                                        new java.sql.Date(System.currentTimeMillis() + 86400000),
                                        "Vamos fazer uma caminhada saudável",
                                        "confirm",
                                        volunteer2,
                                        elderly2);

                        Meeting meeting3 = new Meeting(
                                        "Aula de Zoom",
                                        "Aprender a usar videochamadas",
                                        "remote",
                                        new java.sql.Date(System.currentTimeMillis() + 172800000),
                                        "Vou te ensinar a usar o Zoom",
                                        "pending",
                                        volunteer1,
                                        elderly2);

                        meetingRepository.saveAll(Arrays.asList(meeting2, meeting3));
                        entityManager.flush();

                } catch (Exception e) {
                        logger.error("Error in seedDatabase: ", e);
                        throw e;
                }
        }
}