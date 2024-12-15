package com.pontedegeracoes.api.config;

import com.pontedegeracoes.api.entitys.Meeting;
import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositories.MeetingRepository;
import com.pontedegeracoes.api.repositories.NecessityRepository;
import com.pontedegeracoes.api.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements ApplicationListener<ApplicationReadyEvent> {

        private final UserRepository userRepository;
        private final NecessityRepository necessityRepository;
        private final MeetingRepository meetingRepository;
        private final EntityManager entityManager;

        public DatabaseSeeder(UserRepository userRepository,
                        NecessityRepository necessityRepository,
                        MeetingRepository meetingRepository,
                        EntityManager entityManager) {
                this.userRepository = userRepository;
                this.necessityRepository = necessityRepository;
                this.meetingRepository = meetingRepository;
                this.entityManager = entityManager;
        }

        @Override
        @Transactional
        public void onApplicationEvent(ApplicationReadyEvent event) {
                if (isDatabaseEmpty()) {
                        seedDatabase();
                }
        }

        private boolean isDatabaseEmpty() {
                Long userCount = (Long) entityManager
                                .createQuery("SELECT COUNT(u) FROM User u")
                                .getSingleResult();
                return userCount == 0;
        }

        private void seedDatabase() {
                // Create necessities
                Necessity n1 = new Necessity("Caminhar", "Walking in the park or around the neighborhood");
                Necessity n2 = new Necessity("Cantar", "Singing songs or karaoke");
                Necessity n3 = new Necessity("Dançar", "Dancing to music");
                Necessity n4 = new Necessity("Tecnologia", "Learning and using technology");
                Necessity n5 = new Necessity("Leitura", "Reading books or articles");
                Necessity n6 = new Necessity("Esportes", "Playing or watching sports");
                Necessity n7 = new Necessity("Música", "Listening to or playing music");
                Necessity n8 = new Necessity("Arte", "Engaging in artistic activities");
                Necessity n9 = new Necessity("Meal preparation", "Preparing meals together");
                Necessity n10 = new Necessity("Conversation", "Having conversations and socializing");
                Necessity n11 = new Necessity("Grocery shopping", "Shopping for groceries");
                Necessity n12 = new Necessity("House cleaning", "Cleaning the house");
                Necessity n13 = new Necessity("Transportation", "Providing transportation");
                Necessity n14 = new Necessity("Medication pickup", "Picking up medications from the pharmacy");

                necessityRepository.saveAll(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14));

                User elderly1 = new User("Maria Silva", 75, "elderly", "maria@email.com", "password123", "in person",
                                "São Paulo",
                                "SP");
                User elderly2 = new User("João Santos", 80, "elderly", "joao@email.com", "password123", "remote",
                                "Rio de Janeiro", "RJ");
                User volunteer1 = new User("Ana Pereira", 25, "volunteer", "ana@email.com", "password123", "hybrid",
                                "Curitiba",
                                "PR");
                User volunteer2 = new User("Pedro Costa", 30, "volunteer", "pedro@email.com", "password123",
                                "in person",
                                "Salvador", "BA");
                // Set necessities for each user
                List<Necessity> allNecessities = necessityRepository.findAll();
                Random random = new Random();

                elderly1.setNecessities(new HashSet<>(Arrays.asList(
                                allNecessities.get(random.nextInt(allNecessities.size())),
                                allNecessities.get(random.nextInt(allNecessities.size())))));

                elderly2.setNecessities(new HashSet<>(Arrays.asList(
                                allNecessities.get(random.nextInt(allNecessities.size())),
                                allNecessities.get(random.nextInt(allNecessities.size())))));

                volunteer1.setNecessities(new HashSet<>(Arrays.asList(
                                allNecessities.get(random.nextInt(allNecessities.size())),
                                allNecessities.get(random.nextInt(allNecessities.size())))));

                volunteer2.setNecessities(new HashSet<>(Arrays.asList(
                                allNecessities.get(random.nextInt(allNecessities.size())),
                                allNecessities.get(random.nextInt(allNecessities.size())))));

                userRepository.saveAll(Arrays.asList(elderly1, elderly2, volunteer1, volunteer2));

                /*
                 * // Create meetings
                 * Meeting meeting1 = new Meeting(
                 * "Ajuda com Smartphone",
                 * "Auxiliar no uso básico do smartphone",
                 * "in person",
                 * new Date(),
                 * "Vamos aprender juntos sobre tecnologia!",
                 * "pending",
                 * volunteer1,
                 * elderly1);
                 */

                Meeting meeting2 = new Meeting(
                                "Caminhada no Parque",
                                "Caminhada matinal para exercício",
                                "in person",
                                new Date(System.currentTimeMillis() + 86400000), // Tomorrow
                                "Vamos fazer uma caminhada saudável",
                                "confirm",
                                volunteer2,
                                elderly2);

                Meeting meeting3 = new Meeting(
                                "Aula de Zoom",
                                "Aprender a usar videochamadas",
                                "remote",
                                new Date(System.currentTimeMillis() + 172800000), // Day after tomorrow
                                "Vou te ensinar a usar o Zoom",
                                "pending",
                                volunteer1,
                                elderly2);

                // Save meetings
                meetingRepository.saveAll(Arrays.asList(meeting2, meeting3));

        }
}
