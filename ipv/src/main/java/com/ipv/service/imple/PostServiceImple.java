package com.ipv.service.imple;

import com.ipv.entity.Contact;
import com.ipv.entity.Conversation;
import com.ipv.entity.Post;
import com.ipv.entity.User;
import com.ipv.reporsitory.ConversationRepository;
import com.ipv.reporsitory.PostRepository;
import com.ipv.reporsitory.UserRepository;
import com.ipv.service.ContactService;
import com.ipv.service.EncryptionService;
import com.ipv.service.PostService;
import com.ipv.service.UserService;
import com.ipv.util.Constant;
import com.ipv.util.Util;
import com.ipv.util.wrapper.PauseAndResumeWrapper;
import com.ipv.util.wrapper.PostCount;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * The service implementations implement the actual methods and will be injected into required components(like other services or restAPI layer)
 * <p>
 * There are many common method between the services(like CRUD), so the common part is define in a BaseImple
 * This service implements get the common methods by extends the BaseService
 * The E is the Entity type of the service
 * <p>
 * The further methods will be implements here in later
 */
@Service
public class PostServiceImple extends BaseImple<Post> implements PostService {

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
	private ContactService contactService;

	@Autowired
	private EncryptionService encrytionService;

	//After the injection is done, override the repository in the super class
	@PostConstruct
	public void initParent() {
		super.repository = postRepository;
	}

	// Initialize a post with input user id
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
		post = postRepository.save(post);

		Contact contact = new Contact();
		contact.setAddress(null);
		contact.setNumber(null);
		contact.setPostId(post.getId());
		contactService.save(contact);

		post.setContact(contact);

		return post;
	}

	// Find the post with input user id
	@Override
	public Post findById(int id) {
		Post post = super.findById(id);
		if (post != null) {
			List<Conversation> list = conversationRepository.findByPostId(id);
			list.stream().forEach(c -> c.setData(encrytionService.decrypt(c.getData())));
			post.setConversations(list);
			post.setContact(contactService.findByPostId(id));
		}
		return post;
	}

	// Pause the post with input wrapper
	@Override
	public Post pause(PauseAndResumeWrapper wrapper) {
		Post post = super.findById(wrapper.getId());
		post.setStatus(Constant.POST_STATUS_PAUSED);
		encrypt(wrapper.getId(), wrapper.getKey());
		repository.save(post);
		return post;
	}

	// Resume the post with input wrapper
	@Override
	public Post resume(PauseAndResumeWrapper wrapper) {
		decrypt(wrapper.getId(), wrapper.getKey());
		Post post = super.findById(wrapper.getId());
		post.setStatus(Constant.POST_STATUS_ON_GOING);
		repository.save(post);
		return post;
	}

	// Close the post with input id
	@Override
	public Post close(int id) {
		Post post = super.findById(id);
		post.setStatus(Constant.POST_STATUS_CLOSED);
		post.setUserId(0);
		repository.save(post);

		//remove conversations
		List<Conversation> list = conversationRepository.findByPostId(post.getId());
		list.stream().forEach(c -> {
			conversationRepository.delete(c);
		});
		//remove contact
		contactService.deleteById(contactService.findByPostId(id).getId());

		return post;
	}

	// Get the post with input user id
	@Override
	public Post getByUserId(int userId) {
		Post post = postRepository.findByUserId(userId);
		if (post != null) {
			post.setConversations(conversationRepository.findByPostId(post.getId()));
			post.setStaff(userRepository.findById(post.getStaffId()).get());
			post.setContact(contactService.findByPostId(post.getId()));
		}
		return post;
	}

	// Get a list of posts with input staff id
	@Override
	public List<Post> getByStaffId(int id) {
		List<Post> posts = postRepository.findByStaffId(id);
		for (Post post : posts) {
			User user = userService.findById(post.getUserId());
			User staff = userService.findById(post.getStaffId());
			Util.processUser(user, userRepository);
			Util.processUser(staff, userRepository);
			post.setUser(user);
			post.setStaff(staff);
		}
		return posts;
	}

	// Count total posts
	@Override
	public PostCount getCounts() {
		PostCount count = new PostCount();
		count.setOnGoingPost(postRepository.countByStatus(Constant.POST_STATUS_ON_GOING));
		count.setClosedPost(postRepository.countByStatus(Constant.POST_STATUS_CLOSED));
		count.setPausedPost(postRepository.countByStatus(Constant.POST_STATUS_PAUSED));
		return count;
	}

	// Decrypt the input post with input key
	private void decrypt(int postId, String key) {
		BasicTextEncryptor encryptor = encrytionService.createAnEncryptor(key);
		List<Conversation> list = conversationRepository.findByPostId(postId);
		list.stream().forEach(c -> {
			String edata = encrytionService.decrypt(encryptor, c.getData());
			c.setData(edata);
			conversationRepository.save(c);
		});
	}

	// Encrypt the input post with input key
	private void encrypt(int postId, String key) {
		BasicTextEncryptor encryptor = encrytionService.createAnEncryptor(key);
		List<Conversation> list = conversationRepository.findByPostId(postId);
		list.stream().forEach(c -> {
			c.setData(encrytionService.encrypt(encryptor, c.getData()));
			conversationRepository.save(c);
		});

	}
}
