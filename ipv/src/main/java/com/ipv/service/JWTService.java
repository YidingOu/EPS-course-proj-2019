package com.ipv.service;

import com.ipv.entity.User;
import io.jsonwebtoken.Claims;

public interface JWTService {

    public String createJWT(User user);

    public Claims decodeJWT(String jwt);

}
