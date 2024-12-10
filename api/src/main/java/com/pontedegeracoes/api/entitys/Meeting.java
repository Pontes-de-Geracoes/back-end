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
    @NotBlank
    private double latitude;
    @NotBlank
    private double longitude;
    @NotBlank
    private boolean inPerson;
    @NotBlank
    @Size(max = 250)
    private String description;

    @ElementCollection
    @NotBlank
    private List<User> participants; 

    protected Meeting(){}

    public Meeting(Date date, double latitude, 
                   double longitude, boolean inPerson,
                   String description, List<User> participants){
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
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
            "latitude='" + getLatitude() + "'\n" +
            "longitude='" + getLongitude() + "'\n" +
            "inPerson='" + isInPerson() + "'\n" +
            "description='" + getDescription() + "'\n" +
            "participants='" + getParticipants() + "'\n" +
            "}";
    }

}
