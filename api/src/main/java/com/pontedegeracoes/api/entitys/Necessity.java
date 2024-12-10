package com.pontedegeracoes.api.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    protected Necessity(){}

    public Necessity(String name){
        this.name = name;
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

    @Override
    public String toString() {
        return "{" +
            "name='" + getName() + "'" +
            "}";
    }

}
