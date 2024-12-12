package com.pontedegeracoes.api.entitys;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Users")
public class User {

    //o id do usuario sera gerado automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    private int age;

    @NotBlank
    @Size(max = 60)
    private String userType;

    @NotBlank
    @Size(max = 254)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String password;

    @NotBlank
    @Size(max = 60)
    private String meetingPreference;

    @Size(max = 60)
    private String city;

    @Size(max = 60)
    private String stateInitials;

    /*@ElementCollection
    @NotBlank
    private List<String> necessities;*/

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "meetings_participants",
        joinColumns = @JoinColumn(name = "participants_user_id"),
        inverseJoinColumns = @JoinColumn(name = "meeting_meeting_id")
    )
    private Set<Meeting> meetings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_necessities",
        joinColumns = @JoinColumn(name = "user_user_id"),
        inverseJoinColumns = @JoinColumn(name = "necessities")
    )
    private Set<Necessity> necessities = new HashSet<>();


    //construtores
    protected User() {}
    //TODO: deixar cidade / estado opcional
    public User(String name, int age, String userType, String email, String password,
                String meetingPreference, String city, String stateInitials, Set<Necessity> necessities){
        this.name = name;
        this.age = age;
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.meetingPreference = meetingPreference;
        this.city = city;
        this.stateInitials = stateInitials;
        this.necessities = necessities;
    }

    //getters e setters
    public long getUserId() {
        return this.userId;
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

    public String getUserType() {
        return this.userType;
    }
 
    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getMeetingPreference() {
        return this.meetingPreference;
    }

    public void setMeetingPreference(String meetingPreference) {
        this.meetingPreference = meetingPreference;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateInitials() {
        return this.stateInitials;
    }

    public void setStateInitials(String stateInitials) {
        this.stateInitials = stateInitials;
    }

    public Set<Necessity> getNecessities() {
        return this.necessities;
    }

    public void setNecessities(Set<Necessity> necessities) {
        this.necessities = necessities;
    }

    public Set<Meeting> getMeetings() {
        return this.meetings;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }


    @Override
    public String toString() {
        return "{\n" +
            "name='" + getName() + "'\n" +
            "age='" + getAge() + "'\n" +
            "userType='" + getUserType() + "'\n" +
            "email='" + getEmail() + "'\n" +
            "password='" + getPassword() + "'\n" +
            "meetingPreference='" + getMeetingPreference() + "'\n" +
            "city='" + getCity() + "'\n" +
            "stateInitials='" + getStateInitials() + "'\n" +
            "necessities='" + getNecessities() + "'\n" +
            "}";
    }

}
