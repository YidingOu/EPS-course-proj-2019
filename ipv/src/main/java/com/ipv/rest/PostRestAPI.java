package com.ipv.rest;

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


@RestController
@RequestMapping("/posts")
public class PostRestAPI {
	
	@Autowired
	private PostService service;
	
	@GetMapping
	public List<Post> findAll() {
		return service.findAll();
	}

	@GetMapping("{id}")
	public Post get(@PathVariable int id) {
		
		Post post = service.findById(id);
		if (post == null) {
			throw new NotFoundException("Post id not found - " + id);
		}
		return post;
	}
	
	@PostMapping
	public Post add(@RequestBody Post post) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		post.setId(0);
		service.save(post);
		return post;
	}
	
	
	@PutMapping
	public Post update(@RequestBody Post post) {
		service.save(post);
		return post;
	}
	
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		
		Post post = service.findById(id);
		if (post == null) {
			throw new NotFoundException("post id not found - " + id);
		}
		service.deleteById(id);
		return "Deleted Post id - " + id;
	}
	
}










