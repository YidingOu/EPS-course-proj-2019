package com.ipv.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipv.entity.Conversation;
import com.ipv.exception.NotFoundException;
import com.ipv.service.AuditService;
import com.ipv.service.ConversationService;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /conversations
 * For the interactions related to the conversation entity
 * 
 */
@RestController
@RequestMapping("/conversations")
public class ConversationRestAPI {
	
	@Autowired
	private ConversationService service;
	
	@Autowired
	private AuditService auditService;
	
	//get conversations by id
	@GetMapping("{id}")
	public Conversation get(@PathVariable int id) {
		
		Conversation conversation = service.findById(id);
		if (conversation == null) {
			throw new NotFoundException("Conversation id not found - " + id);
		}
		return conversation;
	}
	
	//get conversations by the hosted post id
	@GetMapping("/by_post/{id}")
	public List<Conversation> getByPost(@PathVariable int id) {
		return service.findByPostId(id);
	}
	
	//create the conversation
	@PostMapping
	public Conversation add(@RequestBody Conversation conversation) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		conversation.setId(0);
		service.save(conversation);
		
		// Add audit
		auditService.addAudit(conversation.getUserId(), conversation.getPostId(), 
				"Conversation created with Post id = " + conversation.getPostId() + 
				" and User id = " + conversation.getUserId());
		
		return conversation;
	}
	
	//update the conversation
	@PutMapping
	public Conversation update(@RequestBody Conversation conversation) {
		service.save(conversation);
		
		// Add audit
		auditService.addAudit(conversation.getUserId(), conversation.getPostId(), 
				"Conversation id = " + conversation.getId() + " updated with Post id = " + conversation.getPostId() + 
				" and User id = " + conversation.getUserId());
		
		return conversation;
	}
	
	//delete the conversation
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		Conversation conversation = service.findById(id);
		if (conversation == null) {
			throw new NotFoundException("conversation id not found - " + id);
		}
		service.deleteById(id);
		
		// Add audit
		auditService.addAudit(conversation.getUserId(), conversation.getPostId(), 
				"Conversation id = " + conversation.getId() + " deleted with Post id = " + conversation.getPostId() + 
				" and User id = " + conversation.getUserId());
		
		return "Deleted Conversation id - " + id;
	}
	
}










