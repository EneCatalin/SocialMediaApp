package com.socialmedia.chat_service.repository;

import com.socialmedia.chat_service.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    List<Participant> findByUserId(UUID userId);

}