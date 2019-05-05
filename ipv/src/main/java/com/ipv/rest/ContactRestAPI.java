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

import com.ipv.entity.Contact;
import com.ipv.exception.NotFoundException;
import com.ipv.service.AuditService;
import com.ipv.service.ContactService;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /contacts
 * For the interactions related to the contact entity
 * 
 */
@RestController
@RequestMapping("/api/contacts")
public class ContactRestAPI {
	
	@Autowired
	private ContactService service;
	
	@Autowired
	private AuditService auditService;
	
	//get entity by contact id
	@GetMapping("{id}")
	public Contact get(@PathVariable int id) {
		
		Contact contact = service.findById(id);
		if (contact == null) {
			throw new NotFoundException("Contact id not found - " + id);
		}
		return contact;
	}
	
	//get entity by associated post id
	@GetMapping("/by_post/{id}")
	public Contact getByPost(@PathVariable int id) {
		Contact contact = service.findByPostId(id);
		if (contact == null) {
			throw new NotFoundException("Contact id not found - " + id);
		}
		return contact;
	}
	
	//create the contact
	@PostMapping
	public Contact add(@RequestBody Contact contact) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
//		contact.setId(0);
		service.save(contact);
		
		// Add audit
		auditService.addAudit(null, contact.getPostId(), 
				"Contact id = " + contact.getId() + " created with Post id = " +contact.getPostId());
		return contact;
	}
	
	//update the contact
	@PutMapping
	public Contact update(@RequestBody Contact contact) {
		service.save(contact);
		
		// Add audit
		auditService.addAudit(null, contact.getPostId(), 
				"Contact id = " + contact.getId() + " updated with Post id = " +contact.getPostId());
		return contact;
	}
	
	//delete the contact
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		Contact contact = service.findById(id);
		if (contact == null) {
			throw new NotFoundException("contact id not found - " + id);
		}
		service.deleteById(id);
		
		// Add audit
		auditService.addAudit(null, contact.getPostId(), 
				"Contact id = " + contact.getId() + " deleted with Post id = " +contact.getPostId());
		return "Deleted Contact id - " + id;
	}
	
}










