package com.ipv.util.wrapper;

/**
 * 
 * The model for the response of the validation
 * 
 */
public class ValidateResponseWapper {
	int state;
	int id;
	String message;
	
	public ValidateResponseWapper(int state, int id, String message) {
		super();
		this.state = state;
		this.id = id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
