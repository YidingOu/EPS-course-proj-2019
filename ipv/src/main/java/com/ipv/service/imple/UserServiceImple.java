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
	public boolean validate(String name, String pass) {
		User user = userRepository.findByName(name);
		return checkPass(user, pass);
	}
	
	//A complete function with hashing and checking will be updated later
	private boolean checkPass(User user, String pass) {
		
		String dbPass = user.getPass();
		System.out.println(dbPass);
		System.out.println(pass);
		return dbPass.equals(pass);
	}

	@Override
	public int validateStaff(String name, String pass) {
		User user = userRepository.findByName(name);
		if (user.getRole() < 1 || !checkPass(user, pass)) { // not staff or fail
			return 0;
		} else {
			return user.getRole();
		}
		
	}

	@Override
	public User saltPassword(User usr) {

		return userRepository.save(usr);
	}
	

}
