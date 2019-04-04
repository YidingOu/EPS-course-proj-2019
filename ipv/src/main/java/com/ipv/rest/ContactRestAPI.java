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

import com.ipv.entity.Contact;
import com.ipv.exception.NotFoundException;
import com.ipv.service.ContactService;


@RestController
@RequestMapping("/contacts")
public class ContactRestAPI {
	
	@Autowired
	private ContactService service;
	
	@GetMapping
	public List<Contact> findAll() {
		return service.findAll();
	}

	@GetMapping("{id}")
	public Contact get(@PathVariable int id) {
		
		Contact contact = service.findById(id);
		if (contact == null) {
			throw new NotFoundException("Contact id not found - " + id);
		}
		return contact;
	}
	
	@PostMapping
	public Contact add(@RequestBody Contact contact) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		contact.setId(0);
		service.save(contact);
		return contact;
	}
	
	
	@PutMapping
	public Contact update(@RequestBody Contact contact) {
		service.save(contact);
		return contact;
	}
	
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		Contact contact = service.findById(id);
		if (contact == null) {
			throw new NotFoundException("contact id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted Contact id - " + id;
	}
	
}










