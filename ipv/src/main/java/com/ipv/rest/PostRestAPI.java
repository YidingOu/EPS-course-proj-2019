package com.ipv.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.ipv.service.AuditService;
import com.ipv.service.PostService;
import com.ipv.util.wrapper.PauseAndResumeWrapper;
import com.ipv.util.wrapper.PostCount;

/**
 * 
 * The REST API layer, injected the service, providing the connections for the frontend
 * 
 * The path start from /posts
 * For the interactions related to the post entity
 * 
 */
@RestController
@RequestMapping("/api/posts")
public class PostRestAPI {

	@Autowired
	private PostService service;
	
	@Autowired
	private AuditService auditService;

	//get all posts
	@GetMapping
	public List<Post> findAll() {
		return service.findAll();
	}

	//get post by id
	@GetMapping("{id}")
	public Post get(@PathVariable int id, HttpServletRequest request) {
		System.out.println((String)request.getSession().getAttribute("sss"));
		Post post = service.findById(id);
		if (post == null) {
			throw new NotFoundException("Post id not found - " + id);
		}
		return post;
	}

	//get the post by user id
	@GetMapping("/by_user/{id}")
	public Post getByUser(@PathVariable int id) {
		Post post = service.getByUserId(id);
		if (post == null) {
			throw new NotFoundException("Post id not found - " + id);
		}
		return post;
	}
	
	//get the post by user id
	@GetMapping("/transfer/{postId}/{newStaffId}")
	public Post postTransfer(@PathVariable int postId, @PathVariable int newStaffId) {
		Post post = service.findById(postId);
		if (post == null) {
			throw new NotFoundException("Post id not found - " + postId);
		}
		
		post.setStaffId(newStaffId);
		service.save(post);
		return post;
	}

	//get the posts by staff id
	@GetMapping("/by_staff/{id}")
	public List<Post> getStaffId(@PathVariable int id) {
		return service.getByStaffId(id);
	}

	//create a post
	@PostMapping
	public Post add(@RequestBody Post post) {
		// just in case they pass an id in JSON ... set id to 0 this is to force a save of new item ... instead of update
		post.setId(0);
		service.save(post);
		
		//Add audit
		auditService.addAudit(post.getUserId(), post.getId(), 
				"Post created with id = " + post.getId() + 
				" and User id = " + post.getUserId() + 
				" and Staff id = " + post.getStaffId());
		return post;
	}

	//update a post
	@PutMapping
	public Post update(@RequestBody Post post) {
		service.save(post);
		
		//Add audit
		auditService.addAudit(post.getUserId(), post.getId(), 
				"Post updated with id = " + post.getId() + 
				" and User id = " + post.getUserId() + 
				" and Staff id = " + post.getStaffId());
		
		return post;
	}

	//delete post
	@DeleteMapping("{id}")
	public String delete(@PathVariable int id) {
		Post post = service.findById(id);
		if (post == null) {
			throw new NotFoundException("post id not found - " + id);
		}
		auditService.addAudit(null, id, "Post closed with id = " + id );
		service.close(id);
		
		return "Closed Post id - " + id;
	}

	//pause the post
	@PostMapping("/pause")
	public Post pause(@RequestBody PauseAndResumeWrapper wrapper) {
		//Add audit
		auditService.addAudit(null, wrapper.getId(), "Post paused with id = " + wrapper.getId());
		return service.pause(wrapper);
	}

	//resume the post
	@PostMapping("/resume")
	public Post resume(@RequestBody PauseAndResumeWrapper wrapper) {
		//Add audit
		auditService.addAudit(null, wrapper.getId(), "Post resumed with id = " + wrapper.getId() );
		return service.resume(wrapper);
	}

	//close the post
	@GetMapping("/close/{id}")
	public Post close(@PathVariable int id) {
		//Add audit
		auditService.addAudit(null, id, "Post closed with id = " + id );
		return service.close(id);
	}
	
	//get the posts by staff id
	@GetMapping("/count_info")
	public PostCount getCountInfo() {
		return service.getCounts();
	}

}










