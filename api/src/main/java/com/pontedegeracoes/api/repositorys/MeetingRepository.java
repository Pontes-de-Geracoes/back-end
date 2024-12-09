package com.pontedegeracoes.api.repositorys;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pontedegeracoes.api.entitys.Meeting;

public interface MeetingRepository extends CrudRepository<Meeting, Long>{
    List<Meeting> findAll();
}
