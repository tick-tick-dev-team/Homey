package com.ticktack.homey;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.ticktack.homey.auth.PrincipalDetailsService;
import com.ticktack.homey.dummy.DummyData;
import com.ticktack.homey.dummy.DummyDataImpl;
import com.ticktack.homey.repository.attach.AttachRepository;

import com.ticktack.homey.repository.attach.JpaAttachRepository;

import com.ticktack.homey.repository.attach.MemoryAttachRepository;
import com.ticktack.homey.repository.comment.CommentRepository;
import com.ticktack.homey.repository.comment.JpaCommentRepository;
import com.ticktack.homey.repository.comment.MemoryCommRepository;

import com.ticktack.homey.repository.home.HomeRepository;
import com.ticktack.homey.repository.home.JpaHomeRepository;
import com.ticktack.homey.repository.home.MemoryHomeRepository;
import com.ticktack.homey.repository.post.JpaPostRepository;
import com.ticktack.homey.repository.post.MemoryPostRepository;
import com.ticktack.homey.repository.post.PostRepository;
import com.ticktack.homey.repository.user.JpaUserRepository;
import com.ticktack.homey.repository.user.MemoryUserRepository;
import com.ticktack.homey.repository.user.UserRepository;
import com.ticktack.homey.service.AttachService;
import com.ticktack.homey.service.AttachServiceImpl;
import com.ticktack.homey.service.CommentService;
import com.ticktack.homey.service.CommentServiceImpl;
import com.ticktack.homey.service.HomeService;
import com.ticktack.homey.service.HomeServiceImpl;
import com.ticktack.homey.service.PostService;
import com.ticktack.homey.service.PostServiceImpl;
import com.ticktack.homey.service.UserService;
import com.ticktack.homey.service.UserServiceImpl;

@Configuration
public class SpringConfig {
	
	private EntityManager em;
	private DataSource datasource;
	
	@Autowired
	public SpringConfig(EntityManager em, DataSource datasource) {
		super();
		this.em = em;
		this.datasource = datasource;
	}
	@Bean
	public PrincipalDetailsService principalDetailsService() {
		return new PrincipalDetailsService();
	}
	
	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	
	@Bean
	public HomeRepository homeRepository() {
		//return new MemoryHomeRepository();
		return new JpaHomeRepository(em);
	}
	
	@Bean
	public HomeService homeService() {
		return new HomeServiceImpl(homeRepository());
	}
	
	@Bean
	public UserRepository userRepository() {
		//return new MemoryUserRepository();
		return new JpaUserRepository(em);
	}
	
	@Bean
	public UserService userService () {
		return new UserServiceImpl(userRepository());
	}
	
	@Bean
	public PostRepository postRepository() {
//		return new MemoryPostRepository();
		return new JpaPostRepository(em);
	}
	
	@Bean
	public PostService postService() {
		return new PostServiceImpl(postRepository(), commentRepository(), attachRepository(), userRepository());
	}
	
	@Bean
	public AttachRepository attachRepository() {
//		return new MemoryAttachRepository();
		return new JpaAttachRepository(em);
	}
	
	@Bean
	public AttachService attachService () {
		return new AttachServiceImpl(attachRepository());
	}
	@Bean
	public CommentRepository commentRepository() {
		return new JpaCommentRepository(em);
//		return new MemoryCommRepository();
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