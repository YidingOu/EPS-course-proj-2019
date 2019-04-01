package com.ipv.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Contact;


public interface ContactRepository extends JpaRepository<Contact, Integer>{

}
