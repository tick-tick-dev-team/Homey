package com.ticktack.homey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticktack.homey.repository.post.MemoryPostRepository;
import com.ticktack.homey.repository.post.PostRepository;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.PostServiceImpl;

@Configuration
public class SpringConfig {
	
	@Bean
	public PostRepository postRepository() {
		return new MemoryPostRepository();
	}
	
	@Bean
	public PostService postService() {
		return new PostServiceImpl(postRepository());
	}
}
