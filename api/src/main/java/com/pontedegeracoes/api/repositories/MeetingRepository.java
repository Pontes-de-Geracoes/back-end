package com.pontedegeracoes.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pontedegeracoes.api.entitys.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
