package com.ipv.service;

import java.util.List;

import com.ipv.entity.Conversation;

/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * <p>
 * The service interface get the common methods by extends the BaseService
 * The other required interfaces are added here
 */
public interface ConversationService extends BaseService<Conversation> {
    // Return a list of conversations with input post id
    public List<Conversation> findByPostId(int id);
}
