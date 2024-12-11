package com.pontedegeracoes.api.entitys;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long meetingId;

    @NotBlank
    @DateTimeFormat(pattern = "dd/mm/yyyy hh:mm:ss")
    private Date date;
    @Size(max = 60)
    private String city;
    @Size(max = 60)
    private String stateInitials;
    @NotBlank
    private boolean inPerson;
    @NotBlank
    @Size(max = 250)
    private String description;

    @ElementCollection
    @NotBlank
    private List<User> participants; 

    protected Meeting(){}

    public Meeting(Date date, String city, 
                   String stateInitials, boolean inPerson,
                   String description, List<User> participants){
        this.date = date;
        this.city = city;
        this.stateInitials = stateInitials;
        this.inPerson = inPerson;
        this.description = description;
        this.participants = participants;
    }


    public long getMeetingId() {
        return this.meetingId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public boolean isInPerson() {
        return this.inPerson;
    }

    public boolean getInPerson() {
        return this.inPerson;
    }

    public void setInPerson(boolean inPerson) {
        this.inPerson = inPerson;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipants() {
        return this.participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "{\n" +
            "date='" + getDate() + "'\n" +
            "city='" + getCity() + "'\n" +
            "stateInitials='" + getStateInitials() + "'\n" +
            "inPerson='" + isInPerson() + "'\n" +
            "description='" + getDescription() + "'\n" +
            "participants='" + getParticipants() + "'\n" +
            "}";
    }

}
