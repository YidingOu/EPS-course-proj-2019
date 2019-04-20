package com.ipv.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ipv.entity.Post;
import com.ipv.exception.NotFoundException;
import com.ipv.service.PostService;
import com.ipv.util.wrapper.QueryByDateWapper;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /posts
 * For the interactions related to the post entity
 * 
 */
@RestController
@RequestMapping("/posts")
public class PostRestAPI {

	@Autowired
	private PostService service;

	//get all posts
	@GetMapping
	public List<Post> findAll() {
		return service.findAll();
	}

	//get post by id
	@GetMapping("{id}")
	public Post get(@PathVariable int id) {

		Post post = service.findById(id);
		if (post == null) {
			throw new NotFoundException("Post id not found - " + id);
		}
		return post;
	}

	//get the post by user id
	@GetMapping("/by_user/{id}")
	public Post getByUser(@PathVariable int id) {
		return null;
	}

	//get the posts by staff id
	@PostMapping("/by_staff")
	public List<Post> getByUser(@RequestBody QueryByDateWapper requeryByDateBody) {
		return Arrays.asList();
	}

	//create a post
	@PostMapping
	public Post add(@RequestBody Post post) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		post.setId(0);
		service.save(post);
		return post;
	}

	//update a post
	@PutMapping
	public Post update(@RequestBody Post post) {
		service.save(post);
		return post;
	}

	//delete post
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		Post post = service.findById(id);
		if (post == null) {
			throw new NotFoundException("post id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted Post id - " + id;
	}

	//pause the post
	@GetMapping("/pause/{id}")
	public Post pause(@PathVariable int id) {
		return service.pause(id);
	}

	//resume the post
	@GetMapping("/resume/{id}")
	public Post resume(@PathVariable int id) {
		return service.resume(id);
	}

	//pause the post
	@GetMapping("/close/{id}")
	public Post close(@PathVariable int id) {
		return service.close(id);
	}

}










