package com.ipv.service.imple;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.ipv.service.EncryptionService;
import com.ipv.util.Constant;

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
public class ConversationServiceImple extends BaseImple<Conversation> implements ConversationService {

    //Spring Dependency injection
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EncryptionService encrytionService;


    //After the injection is done, override the repository in the super class
    @PostConstruct
    public void initParent() {
        super.repository = conversationRepository;
    }

    // Return a list of conversations with input post id
    @Override
    public List<Conversation> findByPostId(int id) {
        List<Conversation> list = conversationRepository.findByPostId(id);
        list.stream().forEach(c -> c.setData(encrytionService.decrypt(c.getData())));
        return list;
    }

    // Return a conversation with input post id
    @Override
    public Conversation findById(int id) {
        Optional<Conversation> result = repository.findById(id);
        Conversation c = null;
        if (result.isPresent()) {
            c = result.get();
        }
        c.setData(encrytionService.decrypt(c.getData()));
        return c;
    }

    // Save a conversation with the input conversation
    @Override
    public Conversation save(Conversation conversation) {
        conversation.setData(encrytionService.encrypt(conversation.getData()));
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
