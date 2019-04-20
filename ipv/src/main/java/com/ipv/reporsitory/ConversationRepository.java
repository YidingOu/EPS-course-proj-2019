package com.ipv.reporsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Conversation;

/**
 * 
 * Data persistence layer communicating with database
 * Spring Data JpaRepository, which implements the CRUD(create, read, update, delete) operation by default
 * If the other operations is required, it will be added with JPQL in this interface later
 * 
 */
public interface ConversationRepository extends JpaRepository<Conversation, Integer>{
	public List<Conversation> findByPostId(int id);

}
