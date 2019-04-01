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

import com.ipv.entity.Email;
import com.ipv.exception.NotFoundException;
import com.ipv.service.EmailService;


@RestController
@RequestMapping("/emails")
public class EmailRestAPI {
	
	@Autowired
	private EmailService service;
	
	@GetMapping
	public List<Email> findAll() {
		return service.findAll();
	}

	@GetMapping("{id}")
	public Email get(@PathVariable int id) {
		
		Email email = service.findById(id);
		if (email == null) {
			throw new NotFoundException("Email id not found - " + id);
		}
		return email;
	}
	
	@PostMapping
	public Email add(@RequestBody Email email) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		email.setId(0);
		service.save(email);
		return email;
	}
	
	
	@PutMapping
	public Email update(@RequestBody Email email) {
		service.save(email);
		return email;
	}
	
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		Email email = service.findById(id);
		if (email == null) {
			throw new NotFoundException("email id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted Email id - " + id;
	}
	
}










