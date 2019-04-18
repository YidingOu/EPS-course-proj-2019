package com.ipv.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name="email")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Email {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private int id;

	@Column(name="userid")
	private int userId;

	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@PrePersist
	void createdAt() {
		this.date = new Date();
	}
	
	@Column(name="email")
	private String email;
	
	@Column(name="code")
	private String code;
	
	@OneToOne(cascade = {CascadeType.DETACH,
						CascadeType.MERGE,
						CascadeType.PERSIST,
						CascadeType.REFRESH})
	@JoinColumn(name="userid", 
				referencedColumnName="id",
				insertable=false, 
				updatable=false)
	private User user;
	
	

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


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
