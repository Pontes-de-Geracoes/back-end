package com.pontedegeracoes.api.entitys;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Chats")
public class Chat {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user1_id", nullable = false)
  @JsonIgnoreProperties({ "chats", "sentMeetings", "receivedMeetings", "password" })
  private User user1;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user2_id", nullable = false)
  @JsonIgnoreProperties({ "chats", "sentMeetings", "receivedMeetings", "password" })
  private User user2;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "meeting_id", nullable = false)
  @JsonIgnoreProperties({ "sender", "recipient" })
  private Meeting meeting;

  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<ChatMessage> messages = new HashSet<>();

  private Date createdAt;

  public Chat() {
    this.createdAt = new Date();
  }

  public Chat(User user1, User user2, Meeting meeting) {
    this.user1 = user1;
    this.user2 = user2;
    this.meeting = meeting;
    this.createdAt = new Date();
  }

  // Getters and setters
  public long getId() {
    return id;
  }

  public User getUser1() {
    return user1;
  }

  public void setUser1(User user1) {
    this.user1 = user1;
  }

  public User getUser2() {
    return user2;
  }

  public void setUser2(User user2) {
    this.user2 = user2;
  }

  public Meeting getMeeting() {
    return meeting;
  }

  public void setMeeting(Meeting meeting) {
    this.meeting = meeting;
  }

  public Set<ChatMessage> getMessages() {
    return messages;
  }

  public void setMessages(Set<ChatMessage> messages) {
    this.messages = messages;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
}