package com.ipv.util;

import com.ipv.entity.User;
import com.ipv.reporsitory.UserRepository;

public class Util {
	// removing the password and salt when user is returned
	public static void processUser(User user, UserRepository repo) {
		repo.detach(user);
		user.setPass(null);
		user.setSalt(null);
	}
}
