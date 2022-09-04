package com.ticktack.homey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticktack.homey.repository.post.MemoryPostRepository;
import com.ticktack.homey.repository.post.PostRepository;
import com.ticktack.homey.repository.user.MemoryUserRepository;
import com.ticktack.homey.repository.user.UserRepository;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.PostServiceImpl;
import com.ticktack.homey.service.UserService;
import com.ticktack.homey.service.UserServiceImpl;

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
	public UserRepository userRepository() {
		return new MemoryUserRepository();
	}
	
	/*@Bean
	public UserService userService() {
		return new UserServiceImpl(userRepository);
	}*/
}
