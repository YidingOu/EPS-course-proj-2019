package com.ipv.service.imple;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Conversation;
import com.ipv.entity.Post;
import com.ipv.entity.User;
import com.ipv.reporsitory.ConversationRepository;
import com.ipv.reporsitory.PostRepository;
import com.ipv.reporsitory.UserRepository;
import com.ipv.service.ConversationService;
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
public class ConversationServiceImple extends BaseImple<Conversation> implements ConversationService{
	
	//Spring Dependency injection
	@Autowired
	private ConversationRepository conversationRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	//After the injection is done, override the repository in the super class
	@PostConstruct
	public void initParent() {
	  super.repository = conversationRepository;
	}
	
	@Override
	public List<Conversation> findByPostId(int id) {
		return conversationRepository.findByPostId(id);
	}
	
	@Override
	public Conversation save(Conversation conversation){
		conversation.setDate(new Date());
		User user = userRepository.findById(conversation.getUserId()).get();
		Post post = postRepository.findById(conversation.getPostId()).get();
		post.setUpdatedDate(new Date());
		if (user.getRole() == 0) {
			conversation.setReply(Constant.CONVERSATION_FROM_USER);
			post.setUpdated(Constant.POST_UPDATE_FROM_USER);
		} else {
			conversation.setReply(Constant.CONVERSATION_FROM_STAFF);
			post.setStatus(Constant.POST_STATUS_ON_GOING);
			post.setUpdated(Constant.POST_UPDATE_FROM_STAFF);
		}
		postRepository.save(post);
		conversation = repository.save(conversation);
		return conversation;
				
	}


}
