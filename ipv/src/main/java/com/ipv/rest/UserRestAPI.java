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
import com.ipv.service.AuditService;
import com.ipv.service.UserService;
import com.ipv.util.Util;
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
	
	@Autowired
	private AuditService auditService;

	//get all users for the admin
	@GetMapping
	public List<User> findAll() {
		List<User> list = service.findAll();
		list.stream().forEach(user -> Util.processUser(user));
		return list;
	}

	//get user by id
	@GetMapping("{id}")
	public User get(@PathVariable int id) {

		User user = service.findById(id);
		if (user == null) {
			throw new NotFoundException("User id not found - " + id);
		}
		Util.processUser(user);
		return user;
	}

	// create an user, the function will be completed later
	@PostMapping
	public User add(@RequestBody User user) {

		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		user.setId(0);
		service.save(user);
		Util.processUser(user);
		
		//Add audit
		auditService.addAudit(user.getId(), null, "User created with id = " + user.getId());
		
		return user;
	}

	// validate
	@PostMapping("/validate")
	public ValidateResponseWapper validate(@RequestBody User user) {
		User resultUser = service.validate(user.getName(), user.getPass());
		if (resultUser != null) {
			//Add audit
			auditService.addAudit(user.getId(), null, "User logins success with id = " + user.getId());
			return new ValidateResponseWapper(1, resultUser, "Success");
		} else {
			//Add audit
			auditService.addAudit(user.getId(), null, "User logins failed with id = " + user.getId());
			return new ValidateResponseWapper(0, null, "The althentication failed");
		}
		
	}

	//get all staffs
	@GetMapping("/staffs")
	public List<User> findAllStaffs() {
		return Arrays.asList();
	}

	// validate for staffs
	@PostMapping("staffs/validate")
	public ValidateResponseWapper validateStaff(@RequestBody User user) {
		User resultUser = service.validateStaff(user.getName(), user.getPass());
		if (resultUser != null) {
			//Add audit
			auditService.addAudit(user.getId(), null, "Staff logins success with id = " + user.getId());
			return new ValidateResponseWapper(resultUser.getRole(), resultUser, "Success");
		} else {
			//Add audit
			auditService.addAudit(user.getId(), null, "Staff logins failed with id = " + user.getId());
			return new ValidateResponseWapper(0, null, "The althentication failed");
		}
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
		Util.processUser(olduser);
		
		//Add audit
		auditService.addAudit(user.getId(), null, "User updated password with id = " + user.getId());
		
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
		
		//Add audit
		auditService.addAudit(user.getId(), null, "User is deleted with id = " + user.getId());
		
		return "Deleted User id - " + id;
	}


}










