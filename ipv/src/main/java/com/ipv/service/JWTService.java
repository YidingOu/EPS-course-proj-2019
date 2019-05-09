package com.ipv.service;

import com.ipv.entity.User;
import com.ipv.util.wrapper.JWTUserInfoWrapper;
/**
 * Data persistence business logic layer
 * The interface-implementation architechture is required by the Spring framework (the multiple implementation is allowed)
 * There are many common interfaces between the services(like CRUD), so the common part is define in a BaseService
 *  
 * The service for JWT related function call
 */
public interface JWTService {

    // Create JWT
    public String createJWT(User user);

    //Decode JWT and return a wrapper class contains user info
    public JWTUserInfoWrapper decodeJWT(String jwt);

    //validate jwt and expiration time
    public JWTUserInfoWrapper validate(String jwt, User user);

    //alternative validation method
    public JWTUserInfoWrapper validate(String jwt);

}
