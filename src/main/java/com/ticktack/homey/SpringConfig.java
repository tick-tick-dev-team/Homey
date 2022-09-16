package com.ticktack.homey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.ticktack.homey.dummy.DummyData;
import com.ticktack.homey.dummy.DummyDataImpl;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.attach.MemoryAttachRepository;
import com.ticktack.homey.repository.comment.CommentRepository;
import com.ticktack.homey.repository.comment.MemoryCommRepository;
import com.ticktack.homey.repository.post.MemoryPostRepository;
import com.ticktack.homey.repository.post.PostRepository;
import com.ticktack.homey.repository.user.MemoryUserRepository;
import com.ticktack.homey.repository.user.UserRepository;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.AttachServiceImpl;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.CommentServiceImpl;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.PostServiceImpl;
import com.ticktack.homey.service.UserService;
import com.ticktack.homey.service.UserServiceImpl;

@Configuration
public class SpringConfig {
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	
	@Bean
	public UserRepository userRepository() {
		return new MemoryUserRepository();
	}
	@Bean
	public UserService userService() {
		return new UserServiceImpl(userRepository());
	}
	
	@Bean
	public PostRepository postRepository() {
		return new MemoryPostRepository();
	}
	
	@Bean
	public PostService postService() {
		return new PostServiceImpl(postRepository(), commentRepository(), attachRepository());
	}
	
	@Bean
	public AttachRepository attachRepository() {
		return new MemoryAttachRepository();
	}
	
	@Bean
	public AttachService attachService () {
		return new AttachServiceImpl(attachRepository());
	}
	@Bean
	public CommentRepository commentRepository() {
		return new MemoryCommRepository();
	}
	@Bean
	public CommentService commentService() {
		return new CommentServiceImpl(commentRepository());
	}
	
	@Bean
	public DummyData dummyData () {
		return new DummyDataImpl(userService(), postService(), attachService(), commentService());
	}
}