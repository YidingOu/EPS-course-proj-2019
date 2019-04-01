package com.ipv.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Conversation;


public interface ConversationRepository extends JpaRepository<Conversation, Integer>{

}
