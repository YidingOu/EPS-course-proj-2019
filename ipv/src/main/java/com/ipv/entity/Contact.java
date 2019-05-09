package com.ipv.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*
 * 
 * The data mapping class -> DB.TABLE into Spring Entity Bean
 * 
 * */
@Entity
@Table(name="contact")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="postid")
	private int postId;
	
	@Column(name="address")
	private String address;
	
	@Column(name="number")
	private String number;
	
	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@PrePersist
	void createdAt() {
		this.date = new Date();
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}




	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}


}
