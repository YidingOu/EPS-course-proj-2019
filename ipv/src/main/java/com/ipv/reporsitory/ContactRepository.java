package com.ipv.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Contact;

/**
 * Data persistence layer communicating with database
 * Spring Data JpaRepository, which implements the CRUD(create, read, update, delete) operation by default
 * If the other operations is required, it will be added with JPQL in this interface later
 */
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    public Contact findByPostId(int id);

}
