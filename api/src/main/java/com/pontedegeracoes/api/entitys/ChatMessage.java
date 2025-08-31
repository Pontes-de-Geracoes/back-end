package com.pontedegeracoes.api.entitys;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ChatMessages")
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "chat_id", nullable = false)
  @JsonIgnoreProperties({ "messages", "user1", "user2", "meeting" })
  private Chat chat;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sender_id", nullable = false)
  @JsonIgnoreProperties({ "chats", "sentMeetings", "receivedMeetings", "password" })
  private User sender;

  @NotBlank(message = "Message content is required")
  private String content;

  private Date sentAt;

  public ChatMessage() {
    this.sentAt = new Date();
  }

  public ChatMessage(Chat chat, User sender, String content) {
    this.chat = chat;
    this.sender = sender;
    this.content = content;
    this.sentAt = new Date();
  }

  // Getters and setters
  public long getId() {
    return id;
  }

  public Chat getChat() {
    return chat;
  }

  public void setChat(Chat chat) {
    this.chat = chat;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getSentAt() {
    return sentAt;
  }

  public void setSentAt(Date sentAt) {
    this.sentAt = sentAt;
  }
}