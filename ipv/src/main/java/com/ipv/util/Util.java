package com.ipv.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;

import com.ipv.entity.User;
import com.ipv.reporsitory.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Util {

	@Value("${spring.jwt.SECRET_KEY}")
	private static String SECRET_KEY;
	// removing the password and salt when user is returned
	public static void processUser(User user, UserRepository repo) {
		repo.detach(user);
		user.setPass(null);
		user.setSalt(null);
	}

}

