package com.pontedegeracoes.api.controllers;

import com.pontedegeracoes.api.entitys.Meeting;
import com.pontedegeracoes.api.entitys.User;
import com.pontedegeracoes.api.repositories.MeetingRepository;
import com.pontedegeracoes.api.repositories.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/meetings")
@Tag(name = "Meetings")
public class MeetingController {

  @Autowired
  private MeetingRepository meetingRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public ResponseEntity<List<Meeting>> getAllMeetings() {
    List<Meeting> meetings = meetingRepository.findAll();
    return ResponseEntity.ok(meetings);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Meeting> getMeetingById(@PathVariable Long id) {
    Optional<Meeting> meeting = meetingRepository.findById(id);
    return meeting.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Meeting> createMeeting(@Valid @RequestBody Meeting meeting) {

    /* Check if the sender and recipient exists before save */
    Optional<User> sender = userRepository.findById(meeting.getSender().getId());
    Optional<User> recipient = userRepository.findById(meeting.getRecipient().getId());

    if (sender.isEmpty() || recipient.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Meeting savedMeeting = meetingRepository.save(meeting);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedMeeting);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Meeting> updateMeeting(@PathVariable Long id, @RequestBody Meeting meetingDetails) {

    Optional<Meeting> meeting = meetingRepository.findById(id);
    if (meeting.isPresent()) {
      Meeting updatedMeeting = meeting.get();
      updatedMeeting.setName(meetingDetails.getName());
      updatedMeeting.setDescription(meetingDetails.getDescription());
      updatedMeeting.setType(meetingDetails.getType());
      updatedMeeting.setMessage(meetingDetails.getMessage());
      updatedMeeting.setDate(meetingDetails.getDate());

      return ResponseEntity.ok(meetingRepository.save(updatedMeeting));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMeeting(@PathVariable Long id) {
    Optional<Meeting> meeting = meetingRepository.findById(id);
    if (meeting.isPresent()) {
      meetingRepository.delete(meeting.get());
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
