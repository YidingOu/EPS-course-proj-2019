package com.ipv.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipv.entity.Email;
import com.ipv.exception.NotFoundException;
import com.ipv.service.EmailService;
import com.ipv.util.Constant;
import com.ipv.util.wrapper.ValidateResponseWapper;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /emails
 * For the interactions related to the email entity
 * 
 */
@RestController
@RequestMapping("/emails")
public class EmailRestAPI {
	
	@Autowired
	private EmailService service;
	
	//validate the code of the email validation
	@GetMapping("validate/{code}")
	public ValidateResponseWapper validateCode(@PathVariable String code) {
		return new ValidateResponseWapper(Constant.SUCCESS, 1, null);
	}
	
	//get email by id
	@GetMapping("{id}")
	public Email get(@PathVariable int id) {
		
		Email email = service.findById(id);
		if (email == null) {
			throw new NotFoundException("Email id not found - " + id);
		}
		return email;
	}
	
	// create email record
	@PostMapping
	public Email add(@RequestBody Email email) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		email.setId(0);
		service.save(email);
		return email;
	}
	
	// delete the email record
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










