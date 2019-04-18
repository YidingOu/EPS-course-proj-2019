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
 * The data mapping class -> DB.TABLE into Spring Entity Bean
 * The relationships (One-One, One-Many ect, will be added later)
 * 
 * */
@Entity
@Table(name="conversation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversation {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private int id;

	@Column(name="postid")
	private int postId;
	
	@Column(name="userid")
	private int userId;
	
	@Column(name="data")
	private String data;

	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name="reply")
	private int reply;

	@PrePersist
	void createdAt() {
		this.date = new Date();
	}
	
	

	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getReply() {
		return reply;
	}



	public void setReply(int reply) {
		this.reply = reply;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
