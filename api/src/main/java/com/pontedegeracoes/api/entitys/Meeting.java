package com.pontedegeracoes.api.entitys;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    @NotBlank(message = "O tipo de reunião é obrigatória")
    @Size(max = 60)
    @Pattern(regexp = "in person|remote|hybrid", message = "O tipo de encontro deve ser  'in person', 'remote', or 'hybrid'")
    private String type;

    private Date date;

    @NotBlank
    private String message;

    @NotBlank(message = "O status é obrigatório.")
    @Pattern(regexp = "pending|canceled|confirm", message = "O status deve ser 'pending', 'canceled', or 'confirm'")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private User recipient;

    protected Meeting() {
    }

    // Modify constructor to include sender and recipient
    public Meeting(String name, String description, String type, Date date,
            String message, String status, User sender, User recipient) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.date = date;
        this.message = message;
        this.status = status;
        this.sender = sender;
        this.recipient = recipient;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Add getters and setters for the relationships
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "{\n" +
                "date='" + getDate() + "'\n" +
                "description='" + getDescription() + "'\n" +
                "status='" + status + "'\n" +
                "message='" + message + "'\n" +
                "type='" + type + "'\n" +
                "name='" + name + "'\n" +
                "id='" + id + "'\n" +
                "}";
    }

}
