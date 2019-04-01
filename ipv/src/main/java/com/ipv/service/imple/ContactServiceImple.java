package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Contact;
import com.ipv.reporsitory.ContactRepository;
import com.ipv.service.ContactService;

@Service
public class ContactServiceImple extends BaseImple<Contact> implements ContactService{
	
	@Autowired
	private ContactRepository contactRepository;
	
	@PostConstruct
	public void initParent() {
	  super.repository = contactRepository;
	}

}
