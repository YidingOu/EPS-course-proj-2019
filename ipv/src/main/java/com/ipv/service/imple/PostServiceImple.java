package com.ipv.service.imple;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.ipv.entity.Post;
import com.ipv.reporsitory.ConversationRepository;
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
	private ConversationRepository conversationRepository;
	
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
		return postRepository.save(post);
	}
	
	@Override
	public Post findById(int id){
		Post post = super.findById(id);
		if (post != null) {
			post.setConversations(conversationRepository.findByPostId(id));
		}
		return post;
	}

	@Override
	public Post pause(int id) {
		Post post = findById(id);
		post.setKey(KeyGenerators.string().generateKey());
		post.setStatus(Constant.POST_STATUS_PAUSED);
		encrypt(post);
		repository.save(post);
		return post;
	}

	@Override
	public Post resume(int id) {
		Post post = findById(id);
		post.setStatus(Constant.POST_STATUS_ON_GOING);
		decrypt(post);
		repository.save(post);
		return post;
	}

	@Override
	public Post close(int id) {
		Post post = findById(id);
		post.setStatus(Constant.POST_STATUS_CLOSED);
		repository.save(post);
		return post;
	}
	
	private void decrypt(Post post) {
		// TODO Auto-generated method stub
		
	}
	
	private void encrypt(Post post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Post getByUserId(int userId) {
		Post post = postRepository.findByUserId(userId);
		if (post != null) {
			post.setConversations(conversationRepository.findByPostId(post.getId()));
		}
		return post;
	}

	@Override
	public List<Post> getByStaffId(int id) {
		return postRepository.findByStaffId(id);
	}
}
