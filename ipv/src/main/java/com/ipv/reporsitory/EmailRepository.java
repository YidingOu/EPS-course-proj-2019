package com.ipv.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Email;


public interface EmailRepository extends JpaRepository<Email, Integer>{

}
