package com.ipv.service;

import java.util.List;

import com.ipv.entity.User;
import com.ipv.reporsitory.UserRepository;

/**
 * 
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * 
 * The service interface get the common methods by extends the BaseService
 * The other interfaces will be added later
 * 
 */
public interface UserService extends BaseService<User>{
	
	//interface for the password validation
	public User validate(String string, String pass);

	public User validateStaff(String name, String pass);
	
	public int loadBalancerForGettingAStaffId();
	
	public UserRepository getUserRepository();
	
	public List<User> getNormalUsers();
	
	public List<User> getStaffs();
	
	public User changePass(User olduser, String pass);

}
