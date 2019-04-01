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

import com.ipv.entity.User;
import com.ipv.exception.NotFoundException;
import com.ipv.service.UserService;
import com.ipv.wrapper.ValidateResponseWapper;


@RestController
@RequestMapping("/users")
public class UserRestAPI {
	
	@Autowired
	private UserService service;
	
	@GetMapping
	public List<User> findAll() {
		List<User> list = service.findAll();
		list.stream().forEach(user -> processUser(user));
		return list;
	}

	@GetMapping("{id}")
	public User get(@PathVariable int id) {
		
		User user = service.findById(id);
		if (user == null) {
			throw new NotFoundException("User id not found - " + id);
		}
		processUser(user);
		return user;
	}
	
	@PostMapping
	public User add(@RequestBody User user) {
		
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		user.setId(0);
		user.setSalt("randomstring");
		service.save(user);
		processUser(user);
		return user;
	}
	
	@PostMapping("/validate")
	public ValidateResponseWapper validate(@RequestBody User user) {
		int result = service.validate(user.getId(), user.getPass()) ? 1 : 0;
		return new ValidateResponseWapper(result, user.getId());
	}
	
	@PutMapping("/pass")
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
	
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		User user = service.findById(id);
		if (user == null) {
			throw new NotFoundException("user id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted User id - " + id;
	}
	
	private void processUser(User user) {
		user.setPass(null);
		user.setSalt(null);
	}
	
}










