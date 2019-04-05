package com.ipv.service.imple;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.User;
import com.ipv.reporsitory.UserRepository;
import com.ipv.service.UserService;

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
public class UserServiceImple extends BaseImple<User> implements UserService{
	
	//Spring Dependency injection
	@Autowired
	private UserRepository userRepository;
	
	//After the injection is done, override the repository in the super class
	@PostConstruct
	public void initParent() {
	  repository = userRepository;
	}
	
	/*
	 * Validate the passwords
	 * It's dummy one without hashing, the actual function will be done later
	 * 
	 * Also, after the first authentication, the token based authentication mechanism will be implement in later
	 * 
	 * */
	@Override
	public boolean validate(int id, String pass) {
		Optional<User> result = repository.findById(id);
		if (result.isPresent()) {
			if (checkPass(result.get())) {
				return true;
			}
		}
		return false;
	}
	
	//A complete function with hashing and checking will be updated later
	private boolean checkPass(User user) {
		String dbPass = user.getPass();
		return dbPass.equals(dbPass);
	}

}
