package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Email;
import com.ipv.reporsitory.EmailRepository;
import com.ipv.service.EmailService;

@Service
public class EmailServiceImple2 extends BaseImple<Email> implements EmailService{
	
	@Autowired
	private EmailRepository emailRepository;
	
	@PostConstruct
	public void initParent() {
	  super.repository = emailRepository;
	}

}
