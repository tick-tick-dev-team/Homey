package com.ticktack.homey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.attach.MemoryAttachRepository;
import com.ticktack.homey.repository.post.MemoryPostRepository;
import com.ticktack.homey.repository.post.PostRepository;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.AttachServiceImpl;
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
	
	@Bean
	public AttachRepository attachRepository() {
		return new MemoryAttachRepository();
	}
	
	@Bean
	public AttachService attachService () {
		return new AttachServiceImpl(attachRepository());
	}
}
