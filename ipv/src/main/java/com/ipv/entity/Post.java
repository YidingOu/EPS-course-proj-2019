package com.ipv.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * The data mapping class -> DB.TABLE into Spring Entity Bean
 * The relationships (One-One, One-Many ect, will be added later)
 * 
 * */
@Entity
@Table(name="post")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private int id;

	@Column(name="userid")
	private int userId;

	@Column(name="staffid")
	private int staffId;
	
	@Column(name="status")
	private String status;
	
	@Column(name="updated")
	private int updated;
	
	@Column(name="key")
	private String key;
	
//	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="userid")
//	private User user;
//	
//	@ManyToOne
//	@JoinColumn(name="staffid")
//	private User staff;
//	
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public User getStaff() {
//		return staff;
//	}
//
//	public void setStaff(User staff) {
//		this.staff = staff;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}
