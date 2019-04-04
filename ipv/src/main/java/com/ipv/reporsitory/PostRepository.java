package com.ipv.reporsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Post;


public interface PostRepository extends JpaRepository<Post, Integer>{

}
