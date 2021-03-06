package com.ipv.service;

import com.ipv.entity.Contact;

/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * <p>
 * The service interface get the common methods by extends the BaseService
 * The other required interfaces are added here
 */
public interface ContactService extends BaseService<Contact> {
    // Return the contact repository with input post id
    public Contact findByPostId(int id);
}
