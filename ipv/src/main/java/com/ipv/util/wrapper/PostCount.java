package com.ipv.util.wrapper;

/**
 * 
 * The model for the count of the posts
 * 
 */
public class PostCount {
	int closedPost;
	int onGoingPost;
	int pausedPost;
	
	public int getClosedPost() {
		return closedPost;
	}
	public void setClosedPost(int closedPost) {
		this.closedPost = closedPost;
	}
	public int getOnGoingPost() {
		return onGoingPost;
	}
	public void setOnGoingPost(int onGoingPost) {
		this.onGoingPost = onGoingPost;
	}
	public int getPausedPost() {
		return pausedPost;
	}
	public void setPausedPost(int pausedPost) {
		this.pausedPost = pausedPost;
	}
	
}
