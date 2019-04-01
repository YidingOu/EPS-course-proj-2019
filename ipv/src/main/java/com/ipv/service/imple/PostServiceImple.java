package com.ipv.service.imple;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ipv.entity.Post;
import com.ipv.reporsitory.PostRepository;
import com.ipv.service.PostService;

@Service
public class PostServiceImple extends BaseImple<Post> implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@PostConstruct
	public void initParent() {
	  super.repository = postRepository;
	}
}
