package com.ipv.service;

import java.util.List;

import com.ipv.entity.Post;
import com.ipv.util.wrapper.PauseAndResumeWrapper;
import com.ipv.util.wrapper.PostCount;

/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * 
 * The service interface get the common methods by extends the BaseService
 * The other required interfaces are added here
 */
public interface PostService extends BaseService<Post> {

    // Get the post with input user id
    public Post getByUserId(int userId);

    // Get a list of posts with input staff id
    public List<Post> getByStaffId(int id);

    // Initialize a post with input user id
    public Post initPost(int userId);

    // Pause the post with input wrapper
    public Post pause(PauseAndResumeWrapper wrapper);

    // Resume the post with input wrapper
    public Post resume(PauseAndResumeWrapper wrapper);

    // Close the post with input id
    public Post close(int id);

    // Count total posts
    public PostCount getCounts();
}
