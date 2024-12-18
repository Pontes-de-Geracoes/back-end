package com.pontedegeracoes.api.entitys;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "O Nome é obrigatório")
    @Size(max = 60, message = "O Nome deve ter no máximo 60 caracteres")
    private String name;

    @NotNull(message = "A Idade é obrigatória")
    private int age;

    @NotBlank(message = "O Tipo é obrigatório")
    @Size(max = 60)
    private String type;

    private String photo;

    @NotBlank(message = "O Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "A Senha é obrigatória")
    private String password;

    @NotBlank(message = "A Preferência de reunião é obrigatória")
    @Size(max = 60)
    @Pattern(regexp = "in person|remote|hybrid", message = "Meeting preference must be 'in person', 'remote', or 'hybrid'")
    private String meetingPreference;

    @NotBlank(message = "A Cidade é obrigatória")
    @Size(max = 60, message = "A Cidade deve ter no máximo 60 caracteres")
    private String town;

    @NotBlank(message = "O Estado é obrigatório")
    @Size(max = 2, message = "O Estado deve ter no máximo 2 caracteres")
    private String state;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "user_necessity", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "necessity_id"))
    private Set<Necessity> necessities = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Meeting> sentMeetings = new HashSet<>();

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Meeting> receivedMeetings = new HashSet<>();

    public Set<Meeting> getSentMeetings() {
        return sentMeetings;
    }

    public void setSentMeetings(Set<Meeting> sentMeetings) {
        this.sentMeetings = sentMeetings;
    }

    public Set<Meeting> getReceivedMeetings() {
        return receivedMeetings;
    }

    public void setReceivedMeetings(Set<Meeting> receivedMeetings) {
        this.receivedMeetings = receivedMeetings;
    }

    public User() {
    }

    public User(String name, int age, String type, String email, String password,
            String meetingPreference, String town, String state) {
        this.name = name;
        this.age = age;
        this.type = type;
        this.email = email;
        this.password = password;
        this.meetingPreference = meetingPreference;
        this.town = town;
        this.state = state;
        this.photo = "";
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTown() {
        return this.town;
    }

    public void setTown(String city) {
        this.town = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMeetingPreference() {
        return meetingPreference;
    }

    public void setMeetingPreference(String meetingPreference) {
        this.meetingPreference = meetingPreference;
    }

    // Add getter and setter for necessities
    public Set<Necessity> getNecessities() {
        return necessities;
    }

    public void setNecessities(Set<Necessity> necessities) {
        this.necessities = necessities;
    }

    // Add helper methods
    public void addNecessity(Necessity necessity) {
        necessities.add(necessity);
        necessity.getUsers().add(this);
    }

    public void removeNecessity(Necessity necessity) {
        necessities.remove(necessity);
        necessity.getUsers().remove(this);
    }

    public Set<Meeting> getMeetings() {
        Set<Meeting> allMeetings = new HashSet<>();
        allMeetings.addAll(sentMeetings);
        allMeetings.addAll(receivedMeetings);
        return allMeetings;
    }

    @Override
    public String toString() {
        return "{\n" +
                "name='" + getName() + "'\n" +
                "age='" + getAge() + "'\n" +
                "type='" + getType() + "'\n" +
                "email='" + getEmail() + "'\n" +
                "password='" + getPassword() + "'\n" +
                "meetingPreference='" + getMeetingPreference() + "'\n" +
                "city='" + getTown() + "'\n" +
                "state='" + getState() + "\n" +
                "}";
    }

}
