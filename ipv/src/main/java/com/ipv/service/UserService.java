package com.ipv.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ipv.entity.User;
import com.ipv.reporsitory.UserRepository;

/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 * 
 * The service interface get the common methods by extends the BaseService
 * The other required interfaces are added here
 */
public interface UserService extends BaseService<User> {

    //interface for the password validation
    public User validate(String string, String pass, HttpServletResponse response);

    // Validate the input name and password of staff
    public User validateStaff(String name, String pass, HttpServletResponse response);

    // LoadBalance
    public int loadBalancerForGettingAStaffId();

    // Return the user repo
    public UserRepository getUserRepository();

    // Return the list of normal users
    public List<User> getNormalUsers();

    // Return the list of staffs
    public List<User> getStaffs();

    // Change the input user's password to input passwords
    public User changePass(User olduser, String pass);

    // Add the input staff
    public User addStaff(User user, HttpServletResponse response);

    // Delete the input user
    public void delete(User user);
    
 // Add users
	public User save(User user, HttpServletResponse response);

}
