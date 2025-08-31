package com.pontedegeracoes.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontedegeracoes.api.entitys.Chat;
import com.pontedegeracoes.api.entitys.ChatMessage;
import com.pontedegeracoes.api.entitys.Meeting;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositories.ChatMessageRepository;
import com.pontedegeracoes.api.repositories.ChatRepository;
import com.pontedegeracoes.api.repositories.MeetingRepository;
import com.pontedegeracoes.api.repositories.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/chats")
@Tag(name = "Chats")
public class ChatController {

  @Autowired
  private ChatRepository chatRepository;

  @Autowired
  private ChatMessageRepository chatMessageRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private MeetingRepository meetingRepository;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @PostMapping("/meeting/{meetingId}")
  public ResponseEntity<Chat> createChatForMeeting(@PathVariable Long meetingId) {
    Optional<Meeting> meetingOpt = meetingRepository.findById(meetingId);
    if (meetingOpt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Meeting meeting = meetingOpt.get();

    // Check if chat already exists
    Optional<Chat> existingChat = chatRepository.findByMeetingId(meetingId);
    if (existingChat.isPresent()) {
      return ResponseEntity.ok(existingChat.get());
    }

    Chat chat = new Chat(meeting.getSender(), meeting.getRecipient(), meeting);
    Chat savedChat = chatRepository.save(chat);

    // Update meeting status to "confirm" when chat is created
    meeting.setStatus("confirm");
    meetingRepository.save(meeting);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedChat);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Chat>> getChatsByUserId(@PathVariable Long userId) {
    if (!userRepository.existsById(userId)) {
      return ResponseEntity.notFound().build();
    }

    List<Chat> chats = chatRepository.findByUserId(userId);
    return ResponseEntity.ok(chats);
  }

  @GetMapping("/{chatId}")
  public ResponseEntity<Chat> getChatById(@PathVariable Long chatId) {
    Optional<Chat> chat = chatRepository.findById(chatId);
    if (chat.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(chat.get());
  }

  @GetMapping("/{chatId}/messages")
  public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long chatId) {
    if (!chatRepository.existsById(chatId)) {
      return ResponseEntity.notFound().build();
    }

    List<ChatMessage> messages = chatMessageRepository.findByChatIdOrderBySentAtAsc(chatId);
    return ResponseEntity.ok(messages);
  }

  @MessageMapping("/chat.sendMessage")
  public void sendMessage(@Payload ChatMessage message) {
    ChatMessage savedMessage = chatMessageRepository.save(message);

    // Send to both users in the chat
    Chat chat = savedMessage.getChat();
    messagingTemplate.convertAndSendToUser(
        String.valueOf(chat.getUser1().getId()),
        "/queue/messages",
        savedMessage);
    messagingTemplate.convertAndSendToUser(
        String.valueOf(chat.getUser2().getId()),
        "/queue/messages",
        savedMessage);
  }

  @PostMapping("/{chatId}/messages")
  public ResponseEntity<ChatMessage> sendMessage(@PathVariable Long chatId,
      @Valid @RequestBody ChatMessage messageRequest) {
    Optional<Chat> chatOpt = chatRepository.findById(chatId);
    Optional<User> senderOpt = userRepository.findById(messageRequest.getSender().getId());

    if (chatOpt.isEmpty() || senderOpt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Chat chat = chatOpt.get();
    User sender = senderOpt.get();

    ChatMessage message = new ChatMessage(chat, sender, messageRequest.getContent());
    ChatMessage savedMessage = chatMessageRepository.save(message);

    // Send real-time notification to both users
    try {
      messagingTemplate.convertAndSendToUser(
          String.valueOf(chat.getUser1().getId()),
          "/queue/messages",
          savedMessage);
      messagingTemplate.convertAndSendToUser(
          String.valueOf(chat.getUser2().getId()),
          "/queue/messages",
          savedMessage);
      System.out
          .println("Message broadcasted to users: " + chat.getUser1().getId() + " and " + chat.getUser2().getId());
    } catch (Exception e) {
      System.err.println("Failed to broadcast message: " + e.getMessage());
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
  }

  // Add a new endpoint to get chat by meeting ID
  @GetMapping("/meeting/{meetingId}")
  public ResponseEntity<Chat> getChatByMeetingId(@PathVariable Long meetingId) {
    Optional<Chat> chat = chatRepository.findByMeetingId(meetingId);
    if (chat.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(chat.get());
  }
}