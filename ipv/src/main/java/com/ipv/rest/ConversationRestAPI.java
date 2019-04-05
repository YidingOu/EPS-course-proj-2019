package com.ipv.rest;

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
import com.ipv.service.ConversationService;


@RestController
@RequestMapping("/conversations")
public class ConversationRestAPI {
	
	@Autowired
	private ConversationService service;
	
	@GetMapping("{id}")
	public Conversation get(@PathVariable int id) {
		
		Conversation conversation = service.findById(id);
		if (conversation == null) {
			throw new NotFoundException("Conversation id not found - " + id);
		}
		return conversation;
	}
	
	@GetMapping("/by_post/{id}")
	public Conversation getByPost(@PathVariable int id) {
		return null;
	}
	
	@PostMapping
	public Conversation add(@RequestBody Conversation conversation) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		conversation.setId(0);
		service.save(conversation);
		return conversation;
	}
	
	
	@PutMapping
	public Conversation update(@RequestBody Conversation conversation) {
		service.save(conversation);
		return conversation;
	}
	
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		Conversation conversation = service.findById(id);
		if (conversation == null) {
			throw new NotFoundException("conversation id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted Conversation id - " + id;
	}
	
}










