package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.User;
import com.ipv.reporsitory.UserRepository;
import com.ipv.service.UserService;

@Service
public class UserServiceImple extends BaseImple<User> implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void initParent() {
	  repository = userRepository;
	}

	@Override
	public boolean validate(int id, String pass) {
		return true;
	}

}
