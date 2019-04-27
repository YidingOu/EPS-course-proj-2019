package com.ipv.service;

import com.ipv.entity.User;
import com.ipv.util.wrapper.JWTUserInfoWrapper;

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
