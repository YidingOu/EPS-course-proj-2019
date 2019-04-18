package com.ipv.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * The data mapping class -> DB.TABLE into Spring Entity Bean
 * The relationships (One-One, One-Many ect, will be added later)
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

	@Column(name = "displayname")
	private String displayName;

	// -1 when the user is not active by the email validation
	@Column(name = "role")
	private int role;

	@Column(name = "salt")
	private String salt;

	@Column(name = "pass")
	private String pass;

	@OneToOne(mappedBy = "user", 
			targetEntity=Email.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	private Email email;

	@OneToOne(mappedBy = "user",
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER)
	private Post post;

	@OneToMany(mappedBy = "staff",
			fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH,
					CascadeType.MERGE,
					CascadeType.PERSIST,
					CascadeType.REFRESH})
	private List<Post> postForStaff;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", displayName=" + displayName + ", role=" + role + ", salt="
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

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
