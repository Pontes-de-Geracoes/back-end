package com.pontedegeracoes.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pontedegeracoes.api.entitys.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

  @Query("SELECT c FROM Chat c WHERE c.meeting.id = :meetingId")
  Optional<Chat> findByMeetingId(@Param("meetingId") Long meetingId);

  @Query("SELECT c FROM Chat c WHERE (c.user1.id = :userId OR c.user2.id = :userId)")
  List<Chat> findByUserId(@Param("userId") Long userId);
}