package com.ipv.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

import com.ipv.entity.User;
import com.ipv.exception.AuthorizationFailedException;
import com.ipv.reporsitory.UserRepository;
import com.ipv.util.wrapper.JWTUserInfoWrapper;

public class Util {

	@Value("${spring.jwt.SECRET_KEY}")
	private static String SECRET_KEY;
	
	// removing the password and salt when user is returned
	public static void processUser(User user, UserRepository repo) {
		repo.detach(user);
		user.setPass(null);
		user.setSalt(null);
	}
	
	public static void authorizationAdmin(HttpServletRequest req) {
		JWTUserInfoWrapper info = (JWTUserInfoWrapper) req.getSession().getAttribute("token");
		if (info.getRole() < 2) {
			new AuthorizationFailedException();
		}
	}

	public static void authorizationStaff(HttpServletRequest req) {
		JWTUserInfoWrapper info = (JWTUserInfoWrapper) req.getSession().getAttribute("token");
		if (info.getRole() < 1) {
			new AuthorizationFailedException();
		}
	}

}

