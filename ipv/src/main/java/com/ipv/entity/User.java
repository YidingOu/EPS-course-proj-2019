package com.ipv.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * The data mapping class -> DB.TABLE into Spring Entity Bean
 * 
 * */
@Entity
@Table(name = "User")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	// -1 when the user is not active by the email validation
	@Column(name = "role")
	private int role;

	@Column(name = "salt")
	private String salt;

	@Column(name = "pass")
	private String pass;

	@Transient
	private Post post;

	@Transient
	private List<Post> postForStaff;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", firstname=" + firstName + ", role=" + role + ", salt="
				+ salt + ", pass=" + pass + "]";
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<Post> getPostForStaff() {
		return postForStaff;
	}

	public void setPostForStaff(List<Post> postForStaff) {
		this.postForStaff = postForStaff;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
