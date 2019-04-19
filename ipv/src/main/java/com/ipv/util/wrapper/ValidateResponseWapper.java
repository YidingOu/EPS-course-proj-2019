package com.ipv.util.wrapper;

import com.ipv.entity.User;

/**
 * 
 * The model for the response of the validation
 * 
 */
public class ValidateResponseWapper {
	int state;
	User entity;
	String message;
	
	public ValidateResponseWapper(int state, User entity, String message) {
		super();
		this.state = state;
		this.entity = entity;
		this.message = message;
	}
	
	
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}


	public User getEntity() {
		return entity;
	}


	public void setEntity(User entity) {
		this.entity = entity;
	}
	
	
}
