package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Conversation;
import com.ipv.reporsitory.ConversationRepository;
import com.ipv.service.ConversationService;

@Service
public class ConversationServiceImple extends BaseImple<Conversation> implements ConversationService{
	
	@Autowired
	private ConversationRepository conversationRepository;
	
	@PostConstruct
	public void initParent() {
	  super.repository = conversationRepository;
	}


}
