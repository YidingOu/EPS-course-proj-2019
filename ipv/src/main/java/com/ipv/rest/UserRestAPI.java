package com.ipv.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipv.entity.User;
import com.ipv.exception.NotFoundException;
import com.ipv.service.UserService;
import com.ipv.util.Constant;
import com.ipv.util.wrapper.ValidateResponseWapper;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /users
 * For the interactions related to the user entity
 * 
 */
@CrossOrigin(origins = "*", maxAge = 3600) //cors issue, for the development usage
@RestController
@RequestMapping("/users")
public class UserRestAPI {
	
	@Autowired
	private UserService service;
	
	//get all users for the admin
	@GetMapping
	public List<User> findAll() {
		List<User> list = service.findAll();
		list.stream().forEach(user -> processUser(user));
		return list;
	}
	
	//get all staffs
	@GetMapping("/staffs")
	public List<User> findAllStaffs() {
		return Arrays.asList();
	}

	//get user by id
	@GetMapping("{id}")
	public User get(@PathVariable int id) {
		
		User user = service.findById(id);
		if (user == null) {
			throw new NotFoundException("User id not found - " + id);
		}
		processUser(user);
		return user;
	}
	
	// create an user, the function will be completed later
	@PostMapping
	public User add(@RequestBody User user) {
		
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		user.setId(0);
		user.setSalt("randomstring");
		service.save(user);
		processUser(user);
		return user;
	}
	
	// validate
	@PostMapping("/validate")
	public ValidateResponseWapper validate(@RequestBody User user) {
		int result = service.validate(user.getId(), user.getPass()) ? Constant.SUCCESS : Constant.FAIL;
		return new ValidateResponseWapper(result, user.getId(), null);
	}
	
	// update password for the user
	@PutMapping("/update_pass")
	public User update(@RequestBody User user) {
		User olduser = service.findById(user.getId());
		if (olduser == null) {
			throw new NotFoundException("User id not found - " + user.getId());
		}
		olduser.setPass(user.getPass());
		service.save(olduser);
		processUser(olduser);
		return olduser;
	}
	
	// delete a user (delete account)
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		User user = service.findById(id);
		if (user == null) {
			throw new NotFoundException("user id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted User id - " + id;
	}
	
	// removing the password and salt when user is returned
	private void processUser(User user) {
		user.setPass(null);
		user.setSalt(null);
	}
	
}










