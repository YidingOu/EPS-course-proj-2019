package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Contact;
import com.ipv.reporsitory.ContactRepository;
import com.ipv.service.ContactService;

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
public class ContactServiceImple extends BaseImple<Contact> implements ContactService{
	
	//Spring Dependency injection
	@Autowired
	private ContactRepository contactRepository;
	
	//After the injection is done, override the repository in the super class
	@PostConstruct
	public void initParent() {
	  super.repository = contactRepository;
	}

	// Return the contact repository with input post id
	@Override
	public Contact findByPostId(int id) {
		return contactRepository.findByPostId(id);
	}

}
