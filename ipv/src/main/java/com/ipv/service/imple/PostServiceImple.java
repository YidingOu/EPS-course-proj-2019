package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Post;
import com.ipv.reporsitory.PostRepository;
import com.ipv.service.PostService;
import com.ipv.service.UserService;
import com.ipv.util.Constant;

/**
 * 
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * The service implementations implement the actual methods and will be injected into required components(like other services or restAPI layer)
 * 
 * There are many common method between the services(like CRUD), so the common part is define in a BaseImple
 * This service implements get the common methods by extends the BaseService
 * The E is the Entity type of the service
 * 
 * The further methods will be implements here in later
 * 
 */
@Service
public class PostServiceImple extends BaseImple<Post> implements PostService{
	
	//Spring Dependency injection
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserService userService;
	
	//After the injection is done, override the repository in the super class
	@PostConstruct
	public void initParent() {
	  super.repository = postRepository;
	}

	@Override
	public Post initPost(int userId) {
		Post post = new Post();
		post.setId(0);
		post.setUserId(userId);
		post.setStaffId(userService.loadBalancerForGettingAStaffId());
		post.setStatus(Constant.POST_STATUS_NEW);
		post.setUpdated(Constant.POST_UPDATE_NO);
		post.setKey("");
		return postRepository.save(post);
	}
}
