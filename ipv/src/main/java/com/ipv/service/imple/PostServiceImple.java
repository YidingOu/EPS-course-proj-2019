package com.ipv.service.imple;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Conversation;
import com.ipv.entity.Post;
import com.ipv.entity.User;
import com.ipv.reporsitory.ConversationRepository;
import com.ipv.reporsitory.PostRepository;
import com.ipv.reporsitory.UserRepository;
import com.ipv.service.EncryptionService;
import com.ipv.service.PostService;
import com.ipv.service.UserService;
import com.ipv.util.Constant;
import com.ipv.util.Util;
import com.ipv.util.wrapper.PauseAndResumeWrapper;
import com.ipv.util.wrapper.PostCount;

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
	private UserRepository userRepository;
	
	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EncryptionService encrytionService;
	
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
		post.setStatus(Constant.POST_STATUS_ON_GOING);
		post.setUpdated(Constant.POST_UPDATE_NO);
		post.setStartDate(new Date());
		post.setUpdatedDate(new Date());
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
	public Post pause(PauseAndResumeWrapper wrapper) {
		Post post = findById(wrapper.getId());
		post.setStatus(Constant.POST_STATUS_PAUSED);
		encrypt(post, wrapper.getKey());
		repository.save(post);
		return post;
	}

	@Override
	public Post resume(PauseAndResumeWrapper wrapper) {
		Post post = findById(wrapper.getId());
		post.setStatus(Constant.POST_STATUS_ON_GOING);
		decrypt(post, wrapper.getKey());
		repository.save(post);
		return post;
	}

	@Override
	public Post close(int id) {
		Post post = findById(id);
		post.setStatus(Constant.POST_STATUS_CLOSED);
		post.setUserId(0);
		repository.save(post);
		
		List<Conversation> list = conversationRepository.findByPostId(post.getId());
		list.stream().forEach(c -> {
			conversationRepository.delete(c);
		});
		
		return post;
	}
	
	@Override
	public Post getByUserId(int userId) {
		Post post = postRepository.findByUserId(userId);
		if (post != null) {
			post.setConversations(conversationRepository.findByPostId(post.getId()));
			post.setStaff(userRepository.findById(post.getStaffId()).get());
		}
		return post;
	}

	@Override
	public List<Post> getByStaffId(int id) {
		List<Post> posts = postRepository.findByStaffId(id);
		for (Post post :posts) {
			User user = userService.findById(post.getUserId());
			User staff = userService.findById(post.getStaffId());
			Util.processUser(user, userRepository);
			Util.processUser(staff, userRepository);
			post.setUser(user);
			post.setStaff(staff);
		}
		return posts;
	}

	@Override
	public PostCount getCounts() {
		PostCount count = new PostCount();
		count.setOnGoingPost(postRepository.countByStatus(Constant.POST_STATUS_ON_GOING));
		count.setClosedPost(postRepository.countByStatus(Constant.POST_STATUS_CLOSED));
		count.setPausedPost(postRepository.countByStatus(Constant.POST_STATUS_PAUSED));
		return count;
	}
	
	private void decrypt(Post post, String key) {
		BasicTextEncryptor encryptor = encrytionService.createAnEncryptor(key);
		List<Conversation> list = conversationRepository.findByPostId(post.getId());
		list.stream().forEach(c -> {
			c.setData(encrytionService.encrypt(encryptor, c.getData()));
			conversationRepository.save(c);
		});
	}
	
	private void encrypt(Post post, String key) {
		BasicTextEncryptor encryptor = encrytionService.createAnEncryptor(key);
		List<Conversation> list = conversationRepository.findByPostId(post.getId());
		list.stream().forEach(c -> {
			c.setData(encrytionService.decrypt(encryptor, c.getData()));
			conversationRepository.save(c);
		});
		
	}
}
