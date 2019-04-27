package com.ipv.util.wrapper;

import java.util.Date;

public class JWTUserInfoWrapper {
	int id;
	int role;
	Date expireDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	@Override
	public String toString() {
		return "JWTUserInfoWrapper [id=" + id + ", role=" + role + ", expireDate=" + expireDate + "]";
	}
	
	
	
	
}
