package com.pontedegeracoes.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pontedegeracoes.api.entitys.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

  @Query("SELECT m FROM Meeting m WHERE m.sender.id = :userId OR m.recipient.id = :userId")
  List<Meeting> findAllByUserId(@Param("userId") Long userId);
}
