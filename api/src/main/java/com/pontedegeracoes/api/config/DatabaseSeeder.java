package com.pontedegeracoes.api.config;

import com.pontedegeracoes.api.entitys.Necessity;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositories.NecessityRepository;
import com.pontedegeracoes.api.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements ApplicationListener<ApplicationReadyEvent> {

  private final UserRepository userRepository;
  private final NecessityRepository necessityRepository;
  private final EntityManager entityManager;

  public DatabaseSeeder(UserRepository userRepository,
      NecessityRepository necessityRepository,
      EntityManager entityManager) {
    this.userRepository = userRepository;
    this.necessityRepository = necessityRepository;
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
    Necessity n1 = new Necessity("Caminhar");
    Necessity n2 = new Necessity("Cantar");
    Necessity n3 = new Necessity("Dançar");
    Necessity n4 = new Necessity("Tecnologia");
    Necessity n5 = new Necessity("Leitura");
    Necessity n6 = new Necessity("Esportes");
    Necessity n7 = new Necessity("Música");
    Necessity n8 = new Necessity("Arte");
    Necessity n9 = new Necessity("Meal preparation");
    Necessity n10 = new Necessity("Conversation");
    Necessity n11 = new Necessity("Grocery shopping");
    Necessity n12 = new Necessity("House cleaning");
    Necessity n13 = new Necessity("Transportation");
    Necessity n14 = new Necessity("Medication pickup");

    necessityRepository.saveAll(Arrays.asList(n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14));

    // Create users
    User user1 = new User(
        "João Silva",
        30,
        "Voluntário",
        "joao.silva@example.com",
        "senha123",
        "in person",
        "São Paulo",
        "SP");

    User user2 = new User(
        "Maria Souza",
        25,
        "Participante",
        "maria.souza@example.com",
        "senha456",
        "remote",
        "Rio de Janeiro",
        "RJ");

    User elderly1 = new User("Maria Silva", 75, "elderly", "maria@email.com", "password123", "in person", "São Paulo",
        "SP");
    User elderly2 = new User("João Santos", 80, "elderly", "joao@email.com", "password123", "remote",
        "Rio de Janeiro", "RJ");
    User volunteer1 = new User("Ana Pereira", 25, "volunteer", "ana@email.com", "password123", "hybrid", "Curitiba",
        "PR");
    User volunteer2 = new User("Pedro Costa", 30, "volunteer", "pedro@email.com", "password123", "in person",
        "Salvador", "BA");
    // Set necessities for each user
    List<Necessity> allNecessities = necessityRepository.findAll();
    Random random = new Random();

    user1.setNecessities(new HashSet<>(Arrays.asList(
        allNecessities.get(random.nextInt(allNecessities.size())),
        allNecessities.get(random.nextInt(allNecessities.size())))));

    user2.setNecessities(new HashSet<>(Arrays.asList(
        allNecessities.get(random.nextInt(allNecessities.size())),
        allNecessities.get(random.nextInt(allNecessities.size())))));

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

    userRepository.saveAll(Arrays.asList(user1, user2, elderly1, elderly2, volunteer1, volunteer2));
  }
}
