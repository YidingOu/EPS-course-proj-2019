package com.ipv.util;

import com.ipv.entity.User;

public class Util {
	// removing the password and salt when user is returned
	public static void processUser(User user) {
		user.setPass(null);
		user.setSalt(null);
	}
}
