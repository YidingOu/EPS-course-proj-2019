package com.ipv.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.User;


public interface UserRepository extends JpaRepository<User, Integer>{

}
