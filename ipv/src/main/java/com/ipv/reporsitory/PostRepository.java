package com.ipv.reporsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipv.entity.Post;

/**
 * 
 * Data persistence layer communicating with database
 * Spring Data JpaRepository, which implements the CRUD(create, read, update, delete) operation by default
 * If the other operations is required, it will be added with JPQL in this interface later
 * 
 */
public interface PostRepository extends JpaRepository<Post, Integer>{
	
	public Post findByUserId(int id);
	
	public List<Post> findByStaffId(int id);

}
