package com.ticktack.homey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

<<<<<<< HEAD
=======
import com.ticktack.homey.dummy.DummyData;
import com.ticktack.homey.dummy.DummyDataImpl;
import com.ticktack.homey.repository.attach.AttachRepository;
import com.ticktack.homey.repository.attach.MemoryAttachRepository;
import com.ticktack.homey.repository.comment.CommentRepository;
import com.ticktack.homey.repository.comment.MemoryCommRepository;
>>>>>>> refs/heads/main
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
	
	/* 게시글 */
	@Bean
	public PostRepository postRepository() {
		return new MemoryPostRepository();
	}
	
	@Bean
	public PostService postService() {
		return new PostServiceImpl(postRepository());
	}
	
<<<<<<< HEAD
=======
	/* 첨부파일 */
	@Bean
	public AttachRepository attachRepository() {
		return new MemoryAttachRepository();
	}
>>>>>>> refs/heads/main
	
	@Bean
	public UserRepository userRepository() {
		return new MemoryUserRepository();
	}
	
<<<<<<< HEAD
	/*@Bean
	public UserService userService() {
		return new UserServiceImpl(userRepository);
	}*/
=======
	/* 댓글, 답글 */
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
		return new DummyDataImpl(postService(), attachService(), commentService());
	}
>>>>>>> refs/heads/main
}
