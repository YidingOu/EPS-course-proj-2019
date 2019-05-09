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
@Table(name="audit")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Audit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="userid")
	private int userId;

	@Column(name="postid")
	private int postId;

	@Column(name="date_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name="action")
	private String action;

	@PrePersist
	void createdAt() {
		this.date = new Date();
	}
	
	@Override
	public String toString() {
		return "Audit [id=" + id + ", userId=" + userId + ", postId=" + postId + ", date=" + date + ", action=" + action
				+ "]";
	}




	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



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
