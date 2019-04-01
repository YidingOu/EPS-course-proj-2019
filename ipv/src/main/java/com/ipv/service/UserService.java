package com.ipv.service;

import com.ipv.entity.User;

public interface UserService extends BaseService<User>{
	public boolean validate(int id, String pass);
}
