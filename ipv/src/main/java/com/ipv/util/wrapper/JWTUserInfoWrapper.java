package com.ipv.util.wrapper;

import java.util.Date;
/**
 * 
 * The model for the user info with JWT
 * 
 */
public class JWTUserInfoWrapper {
	int id;	//user id
	int role; //user role
	Date expireDate;	//expiration date
	String newJWT;	//new JWT generated when validation pass

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRole() {
		return role;
	}
	public String getNewJWT() {return newJWT; }
	public void setNewJWT(String jwt) { this.newJWT = jwt; }
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
