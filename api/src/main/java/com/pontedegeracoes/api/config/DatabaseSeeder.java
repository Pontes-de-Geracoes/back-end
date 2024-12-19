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
import java.sql.Date;

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
                        List<Necessity> necessities = seedNecessities();
                        List<User> users = seedUsers();
                        assignNecessitiesToUsers(users, necessities);
                        seedMeetings(users);
                } catch (Exception e) {
                        logger.error("Error in seedDatabase: ", e);
                        throw e;
                }
        }

        private List<Necessity> seedNecessities() {
                List<Necessity> necessities = Arrays.asList(
                                createNecessity("Caminhar", "Acompanhamento para caminhadas no parque ou no bairro"),
                                createNecessity("Tecnologia Básica",
                                                "Ajuda com uso básico de smartphone e aplicativos"),
                                createNecessity("Companhia para Café",
                                                "Momento de conversa e companhia durante o café"),
                                createNecessity("Jogos de Tabuleiro",
                                                "Parceria para jogos de dama, xadrez ou outros jogos de mesa"),
                                createNecessity("Leitura", "Auxílio na leitura de livros, jornais ou documentos"),
                                createNecessity("Jardinagem",
                                                "Compartilhar conhecimentos sobre cultivo de plantas e flores"),
                                createNecessity("Exercícios Leves", "Acompanhamento em exercícios físicos adaptados"),
                                createNecessity("Música", "Momentos musicais, cantoria ou aprendizado de instrumento"),
                                createNecessity("Artesanato", "Atividades manuais como tricô, crochê ou pintura"),
                                createNecessity("Memória", "Exercícios e jogos para estimulação cognitiva"),
                                createNecessity("Compras", "Acompanhamento em compras no mercado ou shopping"),
                                createNecessity("Internet", "Auxílio com navegação na internet e redes sociais"),
                                createNecessity("Videochamadas", "Ajuda para fazer chamadas de vídeo com familiares"),
                                createNecessity("Organização", "Apoio na organização de documentos e compromissos"),
                                createNecessity("Culinária", "Troca de receitas e momento de cozinhar junto"),
                                createNecessity("Dança", "Aulas de dança ou momentos de dançar juntos"),
                                createNecessity("Fotografia", "Ajuda para tirar e organizar fotos no celular"),
                                createNecessity("Histórias", "Compartilhamento de histórias e experiências de vida"),
                                createNecessity("Idiomas", "Conversação básica em outros idiomas"),
                                createNecessity("Yoga", "Prática de yoga ou exercícios de respiração"),
                                createNecessity("Alongamento", "Exercícios de alongamento e mobilidade"),
                                createNecessity("Meditação", "Prática de meditação e relaxamento"),
                                createNecessity("Cinema", "Companhia para assistir e discutir filmes"),
                                createNecessity("Teatro", "Acompanhamento em atividades teatrais ou espetáculos"),
                                createNecessity("Palavras Cruzadas",
                                                "Resolução conjunta de palavras cruzadas e jogos de palavras"),
                                createNecessity("Passeios", "Companhia para pequenos passeios e caminhadas"),
                                createNecessity("Banco", "Auxílio com operações bancárias online"),
                                createNecessity("Redes Sociais", "Ajuda para usar WhatsApp e outras redes sociais"),
                                createNecessity("Pintura", "Atividades de pintura e expressão artística"),
                                createNecessity("Música e Memória", "Recordação de músicas antigas e karaokê"));
                necessityRepository.saveAll(necessities);
                entityManager.flush();
                return necessities;
        }

        private Necessity createNecessity(String name, String description) {
                return new Necessity(name, description);
        }

        private List<User> seedUsers() {
                List<User> users = Arrays.asList(
                                // Main users for testing
                                createElderlyWithPhoto(
                                                "Dona Maria",
                                                72,
                                                "dona.maria@email.com",
                                                "in person",
                                                "São Paulo",
                                                "SP",
                                                "https://ik.imagekit.io/caulicons/Ponte%20de%20gera%C3%A7%C3%B5es/profile_maria.png?updatedAt=1734559805413"),
                                createVolunteerWithPhoto(
                                                "Juliana",
                                                25,
                                                "juliana@email.com",
                                                "in person",
                                                "São Paulo",
                                                "SP",
                                                "https://ik.imagekit.io/caulicons/Ponte%20de%20gera%C3%A7%C3%B5es/profile_juliana.png?updatedAt=1734559805078"),
                                // Elderly users (Idosos)
                                createElderly("Maria Silva", 75, "maria@email.com", "in person", "São Paulo", "SP"),
                                createElderly("João Santos", 80, "joao@email.com", "remote", "Rio de Janeiro", "RJ"),
                                createElderly("Ana Oliveira", 68, "ana.oliveira@email.com", "hybrid", "Belo Horizonte",
                                                "MG"),
                                createElderly("José Pereira", 73, "jose.pereira@email.com", "in person", "Porto Alegre",
                                                "RS"),
                                createElderly("Francisca Lima", 70, "francisca.lima@email.com", "remote", "Salvador",
                                                "BA"),
                                createElderly("Antônio Costa", 77, "antonio.costa@email.com", "hybrid", "Recife", "PE"),
                                createElderly("Helena Souza", 69, "helena.souza@email.com", "in person", "Fortaleza",
                                                "CE"),
                                createElderly("Carlos Rodrigues", 82, "carlos.rod@email.com", "remote", "Curitiba",
                                                "PR"),
                                createElderly("Terezinha Almeida", 71, "terezinha.alm@email.com", "hybrid", "Manaus",
                                                "AM"),
                                createElderly("Pedro Ferreira", 76, "pedro.ferreira@email.com", "in person", "Brasília",
                                                "DF"),
                                createElderly("Luiza Santos", 74, "luiza.santos@email.com", "remote", "Vitória", "ES"),
                                createElderly("Sebastião Nunes", 78, "sebastiao.nunes@email.com", "hybrid", "Goiânia",
                                                "GO"),
                                createElderly("Raimunda Lima", 72, "raimunda.lima@email.com", "in person", "São Luís",
                                                "MA"),
                                createElderly("Geraldo Soares", 79, "geraldo.soares@email.com", "remote", "Natal",
                                                "RN"),
                                createElderly("Benedita Cruz", 70, "benedita.cruz@email.com", "hybrid", "Florianópolis",
                                                "SC"),

                                // Volunteer users (Voluntários)
                                createVolunteer("Lucas Oliveira", 28, "lucas.oli@email.com", "hybrid", "São Paulo",
                                                "SP"),
                                createVolunteer("Julia Santos", 24, "julia.santos@email.com", "in person",
                                                "Rio de Janeiro", "RJ"),
                                createVolunteer("Matheus Costa", 32, "matheus.costa@email.com", "remote",
                                                "Belo Horizonte", "MG"),
                                createVolunteer("Isabela Lima", 27, "isabela.lima@email.com", "hybrid", "Porto Alegre",
                                                "RS"),
                                createVolunteer("Gabriel Silva", 30, "gabriel.silva@email.com", "in person", "Salvador",
                                                "BA"),
                                createVolunteer("Larissa Ferreira", 25, "larissa.fer@email.com", "remote", "Recife",
                                                "PE"),
                                createVolunteer("Rafael Souza", 29, "rafael.souza@email.com", "hybrid", "Fortaleza",
                                                "CE"),
                                createVolunteer("Beatriz Alves", 26, "beatriz.alves@email.com", "in person", "Curitiba",
                                                "PR"),
                                createVolunteer("Thiago Mendes", 31, "thiago.mendes@email.com", "remote", "Manaus",
                                                "AM"),
                                createVolunteer("Carolina Rocha", 28, "carolina.rocha@email.com", "hybrid", "Brasília",
                                                "DF"),
                                createVolunteer("Bruno Castro", 33, "bruno.castro@email.com", "in person", "Vitória",
                                                "ES"),
                                createVolunteer("Mariana Dias", 23, "mariana.dias@email.com", "remote", "Goiânia",
                                                "GO"),
                                createVolunteer("Felipe Martins", 27, "felipe.martins@email.com", "hybrid", "São Luís",
                                                "MA"),
                                createVolunteer("Amanda Carvalho", 29, "amanda.carv@email.com", "in person", "Natal",
                                                "RN"),
                                createVolunteer("Diego Barbosa", 30, "diego.barbosa@email.com", "remote",
                                                "Florianópolis", "SC"));
                userRepository.saveAll(users);
                entityManager.flush();
                return users;
        }

        private User createElderly(String name, int age, String email, String meetingPreference, String town,
                        String state) {
                return createUser(name, age, "elderly", email, meetingPreference, town, state);
        }

        private User createVolunteer(String name, int age, String email, String meetingPreference, String town,
                        String state) {
                return createUser(name, age, "volunteer", email, meetingPreference, town, state);
        }

        private User createUser(String name, int age, String type, String email, String meetingPreference, String town,
                        String state) {
                User user = new User();
                user.setName(name);
                user.setAge(age);
                user.setType(type);
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("password123"));
                user.setMeetingPreference(meetingPreference);
                user.setTown(town);
                user.setState(state);
                user.setPhoto("");
                return user;
        }

        private void assignNecessitiesToUsers(List<User> users, List<Necessity> necessities) {
                // Assign specific necessities to Dona Maria and Juliana
                Set<Necessity> sharedNecessities = new HashSet<>(Arrays.asList(
                                necessities.get(0), // Caminhar
                                necessities.get(1), // Tecnologia Básica
                                necessities.get(2), // Companhia para Café
                                necessities.get(4), // Leitura
                                necessities.get(7), // Música
                                necessities.get(9) // Memória
                ));

                users.get(0).setNecessities(sharedNecessities); // Dona Maria
                users.get(1).setNecessities(sharedNecessities); // Juliana

                // Random necessities for other users
                for (int i = 2; i < users.size(); i++) {
                        Set<Necessity> userNecessities = new HashSet<>();
                        Random random = new Random();
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        userNecessities.add(necessities.get(random.nextInt(necessities.size())));
                        users.get(i).setNecessities(userNecessities);
                }

                userRepository.saveAll(users);
                entityManager.flush();
        }

        private void seedMeetings(List<User> users) {
                List<Meeting> meetings = Arrays.asList(
                                // Atividades de Tecnologia
                                createMeeting(
                                                "Aprendendo WhatsApp",
                                                "Aula sobre como usar WhatsApp para chamadas de vídeo",
                                                "remote",
                                                daysFromNow(1),
                                                "Vamos aprender a fazer videochamadas com a família",
                                                "pending",
                                                users.get(15), // Lucas (voluntário)
                                                users.get(2) // Maria (idosa)
                                ),
                                createMeeting(
                                                "Curso de Smartphone",
                                                "Aula básica sobre uso do celular",
                                                "in person",
                                                daysFromNow(2),
                                                "Ajuda com configurações e apps básicos",
                                                "confirm",
                                                users.get(16), // Julia
                                                users.get(1) // João
                                ),

                                // Atividades Físicas
                                createMeeting(
                                                "Caminhada no Parque",
                                                "Caminhada leve com acompanhamento",
                                                "in person",
                                                daysFromNow(3),
                                                "Vamos fazer exercício e conversar",
                                                "pending",
                                                users.get(17), // Matheus
                                                users.get(2) // Ana
                                ),
                                createMeeting(
                                                "Yoga para Iniciantes",
                                                "Exercícios básicos de yoga",
                                                "hybrid",
                                                daysFromNow(4),
                                                "Alongamentos e exercícios suaves",
                                                "confirm",
                                                users.get(18), // Isabela
                                                users.get(3) // José
                                ),

                                // Atividades Sociais
                                createMeeting(
                                                "Tarde de Café",
                                                "Momento para conversar e tomar café",
                                                "in person",
                                                daysFromNow(5),
                                                "Vamos compartilhar histórias",
                                                "pending",
                                                users.get(19), // Gabriel
                                                users.get(4) // Francisca
                                ),
                                createMeeting(
                                                "Jogo de Xadrez",
                                                "Partida de xadrez e bate-papo",
                                                "in person",
                                                daysFromNow(6),
                                                "Momento de diversão com jogos de tabuleiro",
                                                "confirm",
                                                users.get(20), // Larissa
                                                users.get(5) // Antônio
                                ),

                                // Atividades Culturais
                                createMeeting(
                                                "Clube do Livro",
                                                "Discussão sobre livros e leitura",
                                                "remote",
                                                daysFromNow(7),
                                                "Vamos compartilhar nossas histórias favoritas",
                                                "pending",
                                                users.get(21), // Rafael
                                                users.get(6) // Helena
                                ),
                                createMeeting(
                                                "Aula de Música",
                                                "Aprendendo músicas antigas",
                                                "in person",
                                                daysFromNow(8),
                                                "Cantoria e recordações musicais",
                                                "confirm",
                                                users.get(22), // Beatriz
                                                users.get(7) // Carlos
                                ),

                                // Atividades Práticas
                                createMeeting(
                                                "Organizando Fotos",
                                                "Ajuda para organizar fotos no celular",
                                                "remote",
                                                daysFromNow(9),
                                                "Vamos organizar suas memórias digitais",
                                                "pending",
                                                users.get(23), // Thiago
                                                users.get(8) // Terezinha
                                ),
                                createMeeting(
                                                "Jardinagem",
                                                "Cultivo de plantas e flores",
                                                "in person",
                                                daysFromNow(10),
                                                "Vamos cuidar do jardim juntos",
                                                "confirm",
                                                users.get(24), // Carolina
                                                users.get(9) // Pedro
                                ),

                                // Atividades de Saúde
                                createMeeting(
                                                "Exercícios de Memória",
                                                "Jogos para estimulação cognitiva",
                                                "hybrid",
                                                daysFromNow(11),
                                                "Atividades divertidas para a mente",
                                                "pending",
                                                users.get(25), // Bruno
                                                users.get(10) // Luiza
                                ),
                                createMeeting(
                                                "Meditação Guiada",
                                                "Sessão de relaxamento e mindfulness",
                                                "remote",
                                                daysFromNow(12),
                                                "Momento de paz e tranquilidade",
                                                "confirm",
                                                users.get(26), // Mariana
                                                users.get(11) // Sebastião
                                ),

                                // Atividades Artísticas
                                createMeeting(
                                                "Pintura em Tela",
                                                "Aula básica de pintura",
                                                "in person",
                                                daysFromNow(13),
                                                "Vamos despertar o artista interior",
                                                "pending",
                                                users.get(27), // Felipe
                                                users.get(12) // Raimunda
                                ),
                                createMeeting(
                                                "Artesanato",
                                                "Oficina de trabalhos manuais",
                                                "in person",
                                                daysFromNow(14),
                                                "Criação de peças artesanais",
                                                "confirm",
                                                users.get(28), // Amanda
                                                users.get(13) // Geraldo
                                ),

                                // Mais Atividades Sociais
                                createMeeting(
                                                "Tarde de Filmes",
                                                "Assistir e discutir filmes clássicos",
                                                "remote",
                                                daysFromNow(15),
                                                "Sessão de cinema virtual",
                                                "pending",
                                                users.get(29), // Diego
                                                users.get(14) // Benedita
                                ),
                                createMeeting(
                                                "Contação de Histórias",
                                                "Compartilhamento de memórias",
                                                "hybrid",
                                                daysFromNow(16),
                                                "Momento de recordar e compartilhar",
                                                "confirm",
                                                users.get(15), // Lucas
                                                users.get(2) // Maria
                                ),

                                // Atividades Educacionais
                                createMeeting(
                                                "Internet Básica",
                                                "Navegação segura na internet",
                                                "remote",
                                                daysFromNow(17),
                                                "Aprendendo a usar a internet com segurança",
                                                "pending",
                                                users.get(16), // Julia
                                                users.get(1) // João
                                ),
                                createMeeting(
                                                "Email e Comunicação",
                                                "Como usar email e mensagens",
                                                "remote",
                                                daysFromNow(18),
                                                "Mantendo contato com amigos e família",
                                                "confirm",
                                                users.get(17), // Matheus
                                                users.get(2) // Ana
                                ),

                                // Atividades de Bem-estar
                                createMeeting(
                                                "Alongamento Matinal",
                                                "Exercícios leves de alongamento",
                                                "hybrid",
                                                daysFromNow(19),
                                                "Começando o dia com energia",
                                                "pending",
                                                users.get(18), // Isabela
                                                users.get(3) // José
                                ),
                                createMeeting(
                                                "Dança Sênior",
                                                "Aula de dança adaptada",
                                                "in person",
                                                daysFromNow(20),
                                                "Movimentando o corpo com alegria",
                                                "confirm",
                                                users.get(19), // Gabriel
                                                users.get(4) // Francisca
                                ),

                                // Atividades Financeiras
                                createMeeting(
                                                "Internet Banking",
                                                "Como usar o banco pelo celular",
                                                "remote",
                                                daysFromNow(21),
                                                "Aprenda a fazer operações bancárias online",
                                                "pending",
                                                users.get(20), // Larissa
                                                users.get(5) // Antônio
                                ),
                                createMeeting(
                                                "Organização Financeira",
                                                "Dicas de controle financeiro",
                                                "hybrid",
                                                daysFromNow(22),
                                                "Organizando contas e pagamentos",
                                                "confirm",
                                                users.get(21), // Rafael
                                                users.get(6) // Helena
                                ),

                                // Atividades de Lazer
                                createMeeting(
                                                "Palavras Cruzadas",
                                                "Resolvendo quebra-cabeças juntos",
                                                "remote",
                                                daysFromNow(23),
                                                "Exercitando o vocabulário",
                                                "pending",
                                                users.get(22), // Beatriz
                                                users.get(7) // Carlos
                                ),
                                createMeeting(
                                                "Clube de Culinária",
                                                "Troca de receitas tradicionais",
                                                "in person",
                                                daysFromNow(24),
                                                "Cozinhando memórias afetivas",
                                                "confirm",
                                                users.get(23), // Thiago
                                                users.get(8) // Terezinha
                                ),
                                createMeeting(
                                                "Caminhada no Parque",
                                                "Caminhada matinal para exercício",
                                                "in person",
                                                daysFromNow(1),
                                                "Vamos fazer uma caminhada saudável",
                                                "confirm",
                                                users.get(3), // Pedro
                                                users.get(1) // João
                                ),
                                createMeeting(
                                                "Aula de Zoom",
                                                "Aprender a usar videochamadas",
                                                "remote",
                                                daysFromNow(2),
                                                "Vou te ensinar a usar o Zoom",
                                                "pending",
                                                users.get(2), // Ana
                                                users.get(1) // João
                                ));
                meetingRepository.saveAll(meetings);
                entityManager.flush();
        }

        private Meeting createMeeting(String name, String description, String type, Date date,
                        String message, String status, User sender, User recipient) {
                return new Meeting(name, description, type, date, message, status, sender, recipient);
        }

        private Date daysFromNow(int days) {
                return new Date(System.currentTimeMillis() + (days * 86400000L));
        }

        private User createElderlyWithPhoto(String name, int age, String email,
                        String meetingPreference, String town, String state, String photo) {
                User user = createElderly(name, age, email, meetingPreference, town, state);
                user.setPhoto(photo);
                return user;
        }

        private User createVolunteerWithPhoto(String name, int age, String email,
                        String meetingPreference, String town, String state, String photo) {
                User user = createVolunteer(name, age, email, meetingPreference, town, state);
                user.setPhoto(photo);
                return user;
        }
}