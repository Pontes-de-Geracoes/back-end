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
    private long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 30, message = "O nome deve ter no máximo 30 caracteres")
    private String name;

    @ManyToMany(mappedBy = "necessities")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    protected Necessity() {
    }

    public Necessity(String name) {
        this.name = name;
        this.users = new HashSet<>();
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

    // Add getter and setter for users
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "{\n" +
                "name='" + getName() + "'\n" +
                ", users='" + getUsers() + "'\n" +
                "}";
    }

}
