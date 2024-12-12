package com.pontedegeracoes.api.entitys;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Necessities")
public class Necessity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long necessityId;

    @NotBlank
    @Size(max = 60)
    private String name;

    @Size(max = 300)
    private String description;

    @ManyToMany(mappedBy = "necessities")
    @JsonIgnore
    private Set<User> users = new HashSet<>();
    
    protected Necessity(){}

    public Necessity(String name, String description){
        this.name = name;
        this.description = description;
    }

    public long getNecessityId() {
        return this.necessityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }


    @Override
    public String toString() {
        return "{\n" +
            "name='" + getName() + "'\n" +
            "description='" + getDescription() + "'\n" +
            "}";
    }

}
