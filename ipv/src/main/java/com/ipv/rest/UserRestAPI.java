package com.ipv.rest;

import com.ipv.entity.User;
import com.ipv.exception.InvalidException;
import com.ipv.exception.NotFoundException;
import com.ipv.reporsitory.UserRepository;
import com.ipv.service.AuditService;
import com.ipv.service.UserService;
import com.ipv.util.Util;
import com.ipv.util.wrapper.ValidateResponseWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /users
 * For the interactions related to the user entity
 * 
 */
@RestController
@RequestMapping("/api/users")
public class UserRestAPI {

	@Autowired
	private UserService service;

	@Autowired
	private AuditService auditService;

	//get all users for the admin
	@GetMapping
	public List<User> findAll(HttpServletRequest req) {
		Util.authorizationStaff(req);
		List<User> list = service.findAll();
		UserRepository userRepository = service.getUserRepository();
		list.stream().forEach(user -> Util.processUser(user, userRepository));
		return list;
	}

	//get user by id
	@GetMapping("{id}")
	public User get(@PathVariable int id, HttpServletRequest req) {
		User user = service.findById(id);
		if (user == null) {
			throw new NotFoundException("User id not found - " + id);
		}
		Util.processUser(user, service.getUserRepository());
		return user;
	}

	// create an user, the function will be completed later
	@PostMapping("/create_user")
	public User add(@RequestBody User user, HttpServletResponse response) {
		checkPassValidation(user);
		user.setId(0);
		service.save(user, response);
		
		//Add audit
		auditService.addAudit(user.getId(), null, "User created with id = " + user.getId());

		return user;
	}

	// create an staff
	@PostMapping("/staffs/create_user")
	public User addStaff(@RequestBody User user, HttpServletResponse response, HttpServletRequest req) {
		Util.authorizationAdmin(req);
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		user.setId(0);
		checkPassValidation(user);
		service.addStaff(user, response);

		//Add audit
		auditService.addAudit(user.getId(), null, "Staff created with id = " + user.getId());

		return user;
	}

	// validate 
	@PostMapping("/validate")
	public ValidateResponseWapper validate(@RequestBody User user, HttpServletResponse response) {
		checkPassValidation(user);
		User resultUser = service.validate(user.getName(), user.getPass(), response);
		
		if (resultUser != null) {
			//Add audit
			auditService.addAudit(resultUser.getId(), null, "User logins success with id = " + resultUser.getId());
			return new ValidateResponseWapper(1, resultUser, "Success");
		} else {
			//Add audit
			auditService.addAudit(resultUser.getId(), null, "User logins failed with id = " + resultUser.getId());
			return new ValidateResponseWapper(0, null, "The althentication failed");
		}

	}

	//get all staffs
	@GetMapping("/staffs")
	public List<User> findAllStaffs(HttpServletRequest req) {
		Util.authorizationAdmin(req);
		return service.getStaffs();
	}

	//get all staffs
	@GetMapping("/customers")
	public List<User> findAllNormalUsers(HttpServletRequest req) {
		Util.authorizationStaff(req);
		return service.getNormalUsers();
	}

	// validate for staffs
	@SuppressWarnings("null")
	@PostMapping("/staffs/validate")
	public ValidateResponseWapper validateStaff(@RequestBody User user, HttpServletResponse response, HttpServletRequest req) {
		checkPassValidation(user);
		User resultUser = service.validateStaff(user.getName(), user.getPass(), response);
		if (resultUser != null) {
			//Add audit
			auditService.addAudit(resultUser.getId(), null, "Staff logins success with id = " + resultUser.getId());
			return new ValidateResponseWapper(resultUser.getRole(), resultUser, "Success");
		} else {
			//Add audit
			auditService.addAudit(resultUser.getId(), null, "Staff login failed with id = " + resultUser.getId());
			return new ValidateResponseWapper(0, null, "Authentication failed");
		}
	}
	
	// udpate staff information
	@PostMapping("/staffs/update")
	public ValidateResponseWapper updateStaff(@RequestBody User user, HttpServletResponse response) {
		User requestedUser = service.findById(user.getId());
		if (requestedUser != null) {
			//update first and last name
			requestedUser.setFirstName(user.getFirstName());
			requestedUser.setLastName(user.getLastName());
			requestedUser.setName(user.getName());
			requestedUser = service.changePass(requestedUser, user.getPass());
			Util.processUser(requestedUser, service.getUserRepository());
			//Add audit
			auditService.addAudit(requestedUser.getId(), null, "Staff updated profile success with id = " + requestedUser.getId());
			return new ValidateResponseWapper(requestedUser.getRole(), requestedUser, "Success");
		} else {
			//Add audit
			auditService.addAudit(requestedUser.getId(), null, "Staff updated profile failed with id = " + requestedUser.getId());
			return new ValidateResponseWapper(0, null, "Update failed");
		}
	}
	
	//update user info
	@PostMapping("/update")
	public ValidateResponseWapper updateUser(@RequestBody User user, HttpServletResponse response) {
		User requestedUser = service.findById(user.getId());
		if (requestedUser != null) {
			//update first and last name
			requestedUser.setName(user.getName());
			requestedUser = service.changePass(requestedUser, user.getPass());
			Util.processUser(requestedUser, service.getUserRepository());
			//Add audit
			auditService.addAudit(requestedUser.getId(), null, "User updated profile success with id = " + requestedUser.getId());
			return new ValidateResponseWapper(requestedUser.getRole(), requestedUser, "Success");
		} else {
			//Add audit
			auditService.addAudit(requestedUser.getId(), null, "User updated profile failed with id = " + requestedUser.getId());
			return new ValidateResponseWapper(0, null, "Update failed");
		}
	}

	// update info for the user
	@PutMapping
	public User update(@RequestBody User user) {
		User olduser = service.findById(user.getId());
		if (olduser == null) {
			throw new NotFoundException("User id not found - " + user.getId());
		}

		olduser.setFirstName(user.getFirstName());
		olduser.setLastName(user.getLastName());
		Util.processUser(olduser, service.getUserRepository());

		//Add audit
		auditService.addAudit(user.getId(), null, "User updated password with id = " + user.getId());

		return olduser;
	}

	// update password for the user
	@PutMapping("/update_pass")
	public User updatePass(@RequestBody User user) {
		checkPassValidation(user);
		User olduser = service.findById(user.getId());
		if (olduser == null) {
			throw new NotFoundException("User id not found - " + user.getId());
		}

		olduser = service.changePass(olduser, user.getPass());
		Util.processUser(olduser, service.getUserRepository());

		//Add audit
		auditService.addAudit(user.getId(), null, "User updated password with id = " + user.getId());

		return olduser;
	}

	// delete a user (delete account)
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id, HttpServletRequest req) {
		Util.authorizationAdmin(req);
		
		User user = service.findById(id);
		if (user == null) {
			throw new NotFoundException("user id not found - " + id);
		}
		service.delete(user);
		
		//Add audit
		auditService.addAudit(user.getId(), null, "User is deleted with id = " + user.getId());

		return "Deleted User id - " + id;
	}
	
	//Check the length of the password
	private void checkPassValidation(User user) {
		String pass = user.getPass();
		if (pass == null || pass.length() < 8 || pass.length() > 20) {
			throw new InvalidException("User Password invalid");
		}
	}


}










