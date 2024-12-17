package com.pontedegeracoes.api.controllers;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

  @GetMapping("/user/{id}")
  public ResponseEntity<List<Meeting>> getMeetingsByUserId(@PathVariable Long id) {
    if (!userRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    List<Meeting> meetings = meetingRepository.findAllByUserId(id);
    return ResponseEntity.ok(meetings);
  }

  @PostMapping
  public ResponseEntity<Meeting> createMeeting(@Valid @RequestBody Meeting meeting) {

    /* Check if the sender and recipient exists before save */
    Optional<User> sender = userRepository.findById(meeting.getSender().getId());
    Optional<User> recipient = userRepository.findById(meeting.getRecipient().getId());

    if (sender.isEmpty() || recipient.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Meeting newMeeting = new Meeting(meeting.getName(), meeting.getDescription(), meeting.getType(), meeting.getDate(),
        meeting.getMessage(), meeting.getStatus(), sender.get(), recipient.get());

    Meeting savedMeeting = meetingRepository.save(newMeeting);
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
      updatedMeeting.setStatus(meetingDetails.getStatus());
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
