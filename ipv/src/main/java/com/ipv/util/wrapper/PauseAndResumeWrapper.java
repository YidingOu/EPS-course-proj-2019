package com.ipv.util.wrapper;

/**
 * 
 * The model for pause and resume use
 * 
 */
public class PauseAndResumeWrapper {
	int id;
	String key;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String toString() {
		return "PauseAndResumeWrapper [id=" + id + ", key=" + key + "]";
	}
	
	
}
