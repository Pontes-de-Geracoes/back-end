package com.pontedegeracoes.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pontedegeracoes.api.entitys.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

  @Query("SELECT cm FROM ChatMessage cm WHERE cm.chat.id = :chatId ORDER BY cm.sentAt ASC")
  List<ChatMessage> findByChatIdOrderBySentAtAsc(@Param("chatId") Long chatId);
}