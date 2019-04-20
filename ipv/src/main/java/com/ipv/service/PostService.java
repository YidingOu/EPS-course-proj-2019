package com.ipv.service;

import java.util.List;

import com.ipv.entity.Post;

/**
 * 
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * 
 * The service interface get the common methods by extends the BaseService
 * The other interfaces will be added later
 * 
 */
public interface PostService extends BaseService<Post>{
	public Post getByUserId(int userId); 
	public List<Post> getByStaffId(int id);
	public Post initPost(int userId); 
	public Post pause(int id);
	public Post resume(int id);
	public Post close(int id);
}
