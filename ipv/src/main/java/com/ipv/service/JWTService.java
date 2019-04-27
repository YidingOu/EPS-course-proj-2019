package com.ipv.service;

import com.ipv.entity.User;
import com.ipv.util.wrapper.JWTUserInfoWrapper;

public interface JWTService {

    public String createJWT(User user);

    public JWTUserInfoWrapper decodeJWT(String jwt);

    public JWTUserInfoWrapper validate(String jwt, User user);

}
