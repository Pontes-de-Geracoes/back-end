package com.pontedegeracoes.entitys;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

    //o id do usuario sera gerado automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, length = 60)
    private String userType;

    @Column(nullable = false, length = 60)
    private String meetingPreference;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @ElementCollection
    @Column(nullable = false)
    private List<String> necessities;

    @ElementCollection
    @Column(nullable = false)
    private List<String> hobbies;

    //construtores
    protected User() {}

    public User(String name, int age, String userType, String meetingPreference,
                double latitude, double longitude, List<String> necessities, List<String> hobbies){
        this.name = name;
        this.age = age;
        this.userType = userType;
        this.meetingPreference = meetingPreference;
        this.latitude = latitude;
        this.longitude = longitude;
        this.necessities = necessities;
        this.hobbies = hobbies;
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

    public String getMeetingPreference() {
        return this.meetingPreference;
    }

    public void setMeetingPreference(String meetingPreference) {
        this.meetingPreference = meetingPreference;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<String> getNecessities() {
        return this.necessities;
    }

    public void setNecessities(List<String> necessities) {
        this.necessities = necessities;
    }

    public List<String> getHobbies() {
        return this.hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "{\n" +
            "name='" + getName() + "'\n" +
            "age='" + getAge() + "'\n" +
            "userType='" + getUserType() + "'\n" +
            "meetingPreference='" + getMeetingPreference() + "'\n" +
            "latitude='" + getLatitude() + "'\n" +
            "longitude='" + getLongitude() + "'\n" +
            "necessities='" + getNecessities() + "'\n" +
            "hobbies='" + getHobbies() + "'\n" +
            "}";
    }

}