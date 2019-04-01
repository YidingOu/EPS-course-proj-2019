package com.ipv.wrapper;

public class ValidateResponseWapper {
	int state;
	int id;
	public ValidateResponseWapper(int state, int id) {
		super();
		this.state = state;
		this.id = id;
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
